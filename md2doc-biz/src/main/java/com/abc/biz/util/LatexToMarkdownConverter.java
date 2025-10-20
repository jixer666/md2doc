package com.abc.biz.util;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * LaTeX数学符号清理工具类
 * 用于将LaTeX数学符号转换为标准Markdown格式
 */
public class LatexToMarkdownConverter {

    // LaTeX 数学环境模式
    private static final Pattern INLINE_MATH = Pattern.compile("\\$([^$]+)\\$");
    private static final Pattern DISPLAY_MATH = Pattern.compile("\\\\\\[([^\\]]+)\\\\\\]");
    private static final Pattern PAREN_MATH = Pattern.compile("\\\\\\(([^)]+)\\\\\\)");

    /**
     * 清理LaTeX数学符号，转换为标准Markdown格式
     * @param content 包含LaTeX数学符号的文本
     * @param format 目标格式：PLAIN-纯文本，DOLLAR-使用$，DOUBLE_DOLLAR-使用$$
     * @return 清理后的文本
     */
    public static String cleanLatexMath(String content, MathFormat format) {
        if (content == null) return null;

        String result = content;

        // 处理行内数学公式 \(...\)
        result = replaceAll(result, PAREN_MATH, match -> {
            String mathContent = match.group(1);
            return format.wrapMath(mathContent);
        });

        // 处理行内数学公式 $...$
        result = replaceAll(result, INLINE_MATH, match -> {
            String mathContent = match.group(1);
            return format.wrapMath(mathContent);
        });

        // 处理显示数学公式 \[...\]
        result = replaceAll(result, DISPLAY_MATH, match -> {
            String mathContent = match.group(1);
            return format.wrapDisplayMath(mathContent);
        });

        // 清理其他LaTeX符号
        result = cleanOtherLatexSymbols(result);

        return result;
    }

    /**
     * 安全的正则替换方法
     */
    private static String replaceAll(String input, Pattern pattern, Function<Matcher, String> replacer) {
        StringBuffer result = new StringBuffer();
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String replacement = replacer.apply(matcher);
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(result);

        return result.toString();
    }

    /**
     * 清理其他LaTeX符号（下标、上标等）
     */
    private static String cleanOtherLatexSymbols(String text) {
        String result = text;

        // 处理下标 _
        result = result.replaceAll("_(\\w+)", "_$1");

        // 处理上标 ^
        result = result.replaceAll("\\^(\\w+)", "^$1");

        // 处理化学式中的下标（特殊情况）
        result = result.replaceAll("KMnO_\\{4\\}", "KMnO₄");
        result = result.replaceAll("H_\\{2\\}O_\\{2\\}", "H₂O₂");

        // 处理其他常见化学符号
        result = result.replaceAll("t_\\{1/2\\}", "t₁/₂");

        // 处理分数
        result = result.replaceAll("\\\\dfrac\\{([^}]+)\\}\\{([^}]+)\\}", "$1/$2");
        result = result.replaceAll("\\\\frac\\{([^}]+)\\}\\{([^}]+)\\}", "$1/$2");

        // 处理数学函数
        result = result.replaceAll("\\\\ln", "ln");
        result = result.replaceAll("\\\\log", "log");
        result = result.replaceAll("\\\\mathrm\\{([^}]+)\\}", "$1");

        return result;
    }

    /**
     * 数学公式格式枚举
     */
    public enum MathFormat {
        /**
         * 纯文本格式（移除所有数学符号）
         */
        PLAIN {
            @Override
            public String wrapMath(String content) {
                return cleanMathContent(content);
            }
            @Override
            public String wrapDisplayMath(String content) {
                return cleanMathContent(content);
            }
        },

        /**
         * 使用$包围的行内数学公式
         */
        DOLLAR {
            @Override
            public String wrapMath(String content) {
                return "$" + cleanMathContent(content) + "$";
            }
            @Override
            public String wrapDisplayMath(String content) {
                return "$$\n" + cleanMathContent(content) + "\n$$";
            }
        },

        /**
         * 使用$$包围的显示数学公式
         */
        DOUBLE_DOLLAR {
            @Override
            public String wrapMath(String content) {
                return "$$" + cleanMathContent(content) + "$$";
            }
            @Override
            public String wrapDisplayMath(String content) {
                return "$$\n" + cleanMathContent(content) + "\n$$";
            }
        };

        public abstract String wrapMath(String content);
        public abstract String wrapDisplayMath(String content);

        /**
         * 清理数学内容中的特殊字符
         */
        private static String cleanMathContent(String content) {
            return content.replaceAll("\\\\,", " ")
                    .replaceAll("\\\\ ", " ")
                    .replaceAll("\\\\quad", "    ")
                    .replaceAll("\\\\qquad", "        ");
        }
    }

    /**
     * 快速清理方法 - 使用默认格式（$包围）
     */
    public static String cleanLatexMath(String content) {
        return cleanLatexMath(content, MathFormat.DOLLAR);
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        String testContent = "速率常数：(k=0.04816\\ \\mathrm{min^{-1}})。\n" +
                           "半衰期：(t_{1/2}=\\dfrac{\\ln2}{k}=14.39\\ \\mathrm{min})。\n" +
                           "线性拟合得到：\\[\\ln x = 8.351014 + (-0.048160),t\\]";
        
        System.out.println("原始内容:");
        System.out.println(testContent);
        System.out.println("\n清理后内容:");
        System.out.println(cleanLatexMath(testContent));
    }
}