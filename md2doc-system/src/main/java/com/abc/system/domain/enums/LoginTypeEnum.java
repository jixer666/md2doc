package com.abc.system.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    ACCOUNT(1, "accountAuthStrategy");
//    PHONE(2, "phoneLoginStrategy"),
//    EMAIL(3, "emailLoginStrategy");

    private final Integer type;
    private final String loginKey;

    public static Integer getTypeByLoginKey(String loginKey) {
        return Arrays.stream(LoginTypeEnum.values()).filter(item -> item.getLoginKey().equals(loginKey)).findFirst().map(LoginTypeEnum::getType).orElse(null);
    }

    public static Integer getTypeByLoginType(Integer loginType) {
        return Arrays.stream(LoginTypeEnum.values()).filter(item -> item.getType().equals(loginType)).findFirst().map(LoginTypeEnum::getType).orElse(null);
    }
}
