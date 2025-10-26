package com.abc.biz.constant;

public class BizConstants {

    public static final String EXPORT_STR = "export";

    public static final String DOCX_EXTENSION = ".docx";

    public static final String MD_EXTENSION = ".md";

    public static final String ADMIN_ACCOUNT = "admin";

    public static final String AI_PROMPT = "帮我修改这段由大模型输出的内容，要求如下：\n" +
            "1、修改为 markdown 能够识别的内容，特别是 latex 数学符号改为 $ 包裹等特殊内容\n" +
            "2、只用给出输出后的内容，无需其他的提示内容\n" +
            "3、我的原内容为：\n" +
            "%s";
}
