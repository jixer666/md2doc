package com.abc.biz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownLatexCleaner {

    public static String cleanLatex(String input) {
        if (input == null || input.isEmpty()) return input;

        // 先去掉 \[ \] \( \) 的反斜杠
        input = input.replaceAll("\\\\([\\[\\]\\(\\)])", "$1");

        // 1️⃣ 保留数据行
        String result = protectDataLines(input);

        // 2️⃣ 保护 Markdown 图片 ![alt](url) 或 !$alt$($url$)
        result = protectImages(result);

        // 3️⃣ 标记数学公式
        result = protectAndMarkMath(result);

        // 4️⃣ 还原 Markdown 数学公式
        result = finalizeMarkedMath(result);

        // 5️⃣ 清理无用空格
        result = result.replaceAll("\\\\\\\\", "\\\\")
                .replaceAll("\\\\ ", " ")
                .trim();

        return result;
    }

    /** 保留 t=... / x=... 数据行 */
    private static String protectDataLines(String text) {
        Pattern pattern = Pattern.compile("(?m)^(\\s*(t|x)\\s*=\\s*\\[.*?\\])$");
        Matcher matcher = pattern.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, Matcher.quoteReplacement(matcher.group(1)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /** 保护 Markdown 图片不被处理 */
    private static String protectImages(String text) {
        Pattern imgPattern = Pattern.compile("!\\[[^\\]]*\\]\\([^)]*\\)");
        Matcher matcher = imgPattern.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "IMGPATTERN_START" + matcher.group() + "IMGPATTERN_END");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /** 匹配数学公式部分 */
    private static String protectAndMarkMath(String text) {
        if (text == null) return text;

        // 圆括号公式
        Pattern parenPattern = Pattern.compile("\\(([^()]*+(?:\\([^()]*+\\)[^()]*)*+)\\)");
        Matcher mp = parenPattern.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (mp.find()) {
            String inner = mp.group(1).trim();
            if (inner.matches(".*(\\\\|=|_|\\^|frac|mathrm|text|ln).*")) {
                mp.appendReplacement(sb, Matcher.quoteReplacement("MATHPAREN_START" + inner + "MATHPAREN_END"));
            } else if (inner.matches("[a-zA-Z0-9_]+")) {
                mp.appendReplacement(sb, Matcher.quoteReplacement("VARPAREN_START" + inner + "VARPAREN_END"));
            } else {
                mp.appendReplacement(sb, Matcher.quoteReplacement("(" + inner + ")"));
            }
        }
        mp.appendTail(sb);
        text = sb.toString();

        // 方括号公式
        Pattern bracketPattern = Pattern.compile("\\[(.*?)\\]", Pattern.DOTALL);
        Matcher mb = bracketPattern.matcher(text);
        sb = new StringBuffer();
        while (mb.find()) {
            String inner = mb.group(1);
            String replacement;
            if (inner.contains("\n")) {
                replacement = "DISPLAYMATH_START" + inner + "DISPLAYMATH_END";
            } else {
                replacement = "SINGLELINE_START" + inner + "SINGLELINE_END";
            }
            mb.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        mb.appendTail(sb);

        return sb.toString();
    }

    /** 将标记还原为 Markdown 数学公式，并恢复图片 */
    private static String finalizeMarkedMath(String text) {
        if (text == null) return text;

        // 圆括号复杂公式 → $ ... $
        text = text.replaceAll("MATHPAREN_START(.*?)MATHPAREN_END", "\\$$1\\$");

        // 圆括号变量 → $ ... $
        text = text.replaceAll("VARPAREN_START(.*?)VARPAREN_END", "\\$$1\\$");

        // 多行方括号 → $$ ... $$
        Pattern multiLinePattern = Pattern.compile("DISPLAYMATH_START(.*?)DISPLAYMATH_END", Pattern.DOTALL);
        Matcher mMulti = multiLinePattern.matcher(text);
        StringBuffer sbMulti = new StringBuffer();
        while (mMulti.find()) {
            String inner = mMulti.group(1);
            String replacement = "\n$$\n" + inner + "\n$$\n";
            mMulti.appendReplacement(sbMulti, Matcher.quoteReplacement(replacement));
        }
        mMulti.appendTail(sbMulti);
        text = sbMulti.toString();

        // 单行方括号 → $ ... $
        text = text.replaceAll("SINGLELINE_START(.*?)SINGLELINE_END", "\\$$1\\$");

        // 恢复 Markdown 图片
        text = text.replaceAll("IMGPATTERN_START(.*?)IMGPATTERN_END", "$1");

        return text;
    }
}
