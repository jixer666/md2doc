package com.abc.biz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MdLatexUtils {

    private static Pattern FANG = Pattern.compile("\\[(.*?)\\]");
    private static Pattern FANG_N = Pattern.compile("\\[\\n(.*?)\\n\\]", Pattern.DOTALL);


    public static String transContent(String content) {
        StringBuilder result = new StringBuilder(content);
        result = handleFangBracket(result);

        System.out.println(result.toString());
        return content;

    }

    private static StringBuilder handleFangBracket(StringBuilder content) {
        Matcher matcher = FANG.matcher(content);
        Matcher matcherN = FANG_N.matcher(content);

        while (matcher.find()) {
            String mainContent = matcher.group(1);
            int startIndex = matcher.start() + 1;
            int endIndex = matcher.end() - 1;

            System.out.printf("内容: %s, 起始下标: %d, 结束下标: %d%n", mainContent, startIndex, endIndex);
            content = replaceContentValue(content, startIndex - 1, "$");
            content = replaceContentValue(content, endIndex, "$");
        }

        while (matcherN.find()) {
            String mainContent = matcherN.group(1);
            int startIndex = matcherN.start() + 1;
            int endIndex = matcherN.end() - 1;

//            System.out.printf("内容: %s, 起始下标: %d, 结束下标: %d%n", mainContent, startIndex, endIndex);
            content = replaceContentValue(content, startIndex - 1, "$$\\n");
            content = replaceContentValue(content, endIndex, "\\n$$");
        }

        return content;

    }


    public static StringBuilder replaceContentValue(StringBuilder str, int index, String replacement) {
        if (str == null || replacement == null) return str;
        if (index < 0 || index >= str.length()) return str;

        StringBuilder sb = new StringBuilder(str);
        sb.replace(index, index + 1, replacement);
        return sb;
    }



    public static void main(String[] args) {

        String str = "**结论**\n" +
                "\n" +
                "1. 反应级数：**一阶反应（相对于 H(_2)O(_2)）**。\n" +
                "2. 速率常数：(k=0.04816\\ \\mathrm{min^{-1}})。\n" +
                "3. 半衰期：(t_{1/2}=\\dfrac{\\ln2}{k}=14.39\\ \\mathrm{min})。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# 数据与处理思路\n" +
                "\n" +
                "原始数据（时间 t / min，对应 (x) / cm³）：\n" +
                "t = [124,127,130,133,136,139,142]\n" +
                "x = [10.60, 9.40, 8.25, 7.00, 6.05, 5.25, 4.50]\n" +
                "\n" +
                "因 KMnO(_4) 的消耗量 (x) 与被测物（H(_2)O(_2)）的浓度成正比，因此可直接用 (x) 代替浓度作动力学检验。\n" +
                "\n" +
                "检验 0、1、2 级的线性关系：\n" +
                "\n" +
                "* 0 级：(x) 对 (t) 作图（应直线：(x=x_0-kt)）\n" +
                "* 1 级：(ln x) 对 (t) 作图（应直线：(ln x=ln x_0 - kt)）\n" +
                "* 2 级：(1/x) 对 (t) 作图（应直线：(1/x=1/x_0 + kt)）\n" +
                "\n" +
                "对三种情形做线性拟合并比较决定系数 (R^2)：\n" +
                "\n" +
                "* 0 级 (x) vs (t)：(R^2=0.9908)\n" +
                "* 1 级 (\\ln x) vs (t)：(R^2=0.99856)（最好）\n" +
                "* 2 级 (1/x) vs (t)：(R^2=0.9785)\n" +
                "\n" +
                "因此判定为**一阶反应**。\n" +
                "\n" +
                "# 拟合结果（用于一阶）\n" +
                "\n" +
                "线性拟合得到：\n" +
                "[\n" +
                "\\ln x = 8.351014 + (-0.048160),t\n" +
                "]\n" +
                "根据一阶动力学：(\\ln x = \\ln x_0 - k t)，得 (k = -(\\text{slope}) = 0.048160\\ \\mathrm{min^{-1}})。\n" +
                "\n" +
                "半衰期：\n" +
                "[\n" +
                "t_{1/2}=\\frac{\\ln 2}{k}=\\frac{0.693147}{0.048160}=14.39\\ \\mathrm{min}.\n" +
                "]\n" +
                "\n" +
                "# 图像\n" +
                "\n" +
                "我已为一阶检验作出 **(\\ln x) vs (t)** 的散点图并画出拟合直线（图片已展示）。拟合方程、(R^2)、(k)、(t_{1/2}) 也在图下给出。";

        transContent(str);
    }


}



