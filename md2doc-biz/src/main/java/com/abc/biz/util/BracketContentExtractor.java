package com.abc.biz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提取字符串中 ()、[]、{} 包裹的内容
 */
public class BracketContentExtractor {

    // 匹配三种括号的正则表达式（非贪婪匹配，避免跨括号匹配）
    // 注意：正则中使用分组捕获括号内的内容，忽略括号本身
    private static final String PATTERN_STR = "\\(([^()]+)\\)|\\[([^\\[\\]]+)\\]|\\{([^\\{\\}]+)\\}";
    private static final Pattern PATTERN = Pattern.compile(PATTERN_STR);

    /**
     * 提取所有括号内的内容（不区分括号类型）
     * @param input 原始字符串
     * @return 所有括号内的内容列表（按出现顺序）
     */
    public static List<String> extractAll(String input) {
        List<String> result = new ArrayList<>();
        if (input == null || input.isEmpty()) {
            return result;
        }

        Matcher matcher = PATTERN.matcher(input);
        while (matcher.find()) {
            // 三个分组分别对应 ()、[]、{} 内的内容，取非 null 的那个
            for (int i = 1; i <= 3; i++) {
                String group = matcher.group(i);
                if (group != null) {
                    result.add(group);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 仅提取 () 内的内容
     * @param input 原始字符串
     * @return () 内的内容列表
     */
    public static List<String> extractParentheses(String input) {
        return extractByPattern(input, "\\(([^()]+)\\)");
    }

    /**
     * 仅提取 [] 内的内容
     * @param input 原始字符串
     * @return [] 内的内容列表
     */
    public static List<String> extractBrackets(String input) {
        return extractByPattern(input, "\\[([^\\[\\]]+)\\]");
    }

    /**
     * 仅提取 {} 内的内容
     * @param input 原始字符串
     * @return {} 内的内容列表
     */
    public static List<String> extractBraces(String input) {
        return extractByPattern(input, "\\{([^\\{\\}]+)\\}");
    }

    /**
     * 根据指定正则提取内容（通用方法）
     */
    private static List<String> extractByPattern(String input, String regex) {
        List<String> result = new ArrayList<>();
        if (input == null || input.isEmpty()) {
            return result;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            // 分组 1 为括号内的内容（忽略括号本身）
            result.add(matcher.group(1));
        }
        return result;
    }

    // 测试示例
    public static void main(String[] args) {
        String testStr = "速率常数：(k=0.04816)，线性拟合：[ln x = 8.35 + (-0.048)t]，" +
                        "其他参数：{a=1, b=2}，嵌套测试：(outer (inner) outer) 不支持嵌套提取";

        System.out.println("原始字符串：" + testStr);
        System.out.println("\n所有括号内容：" + extractAll(testStr));
        System.out.println("() 内容：" + extractParentheses(testStr));
        System.out.println("[] 内容：" + extractBrackets(testStr));
        System.out.println("{} 内容：" + extractBraces(testStr));
    }
}