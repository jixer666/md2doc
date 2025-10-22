package com.abc.common.constant;

public class CacheConstants {

    public static final String SYSTEM_NAME = "md2doc:";

    public static final String LOGIN_TOKEN_KEY = "loginToken:%s";

    public static final String CAPTCHA_UUID = "captchaUuid:%s";
    public static final Long CAPTCHA_UUID_EXPIRE_TIME = 5L;

    public static final String EMAIL_UUID = "emailUuid:%s";
    public static final Long EMAIL_UUID_EXPIRE_TIME = 5L;

    public static String getFinalKey(String key, Object ...values) {
        return String.format(SYSTEM_NAME + key, values);
    }





}
