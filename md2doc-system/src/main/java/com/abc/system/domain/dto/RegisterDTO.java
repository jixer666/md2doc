package com.abc.system.domain.dto;

import com.abc.common.util.AssertUtils;
import lombok.Data;

@Data
public class RegisterDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户密码
     */
    private String passwordAgain;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;

    /**
     * 认证方式
     */
    private Integer authType;

    public void checkParams() {
        AssertUtils.isNotEmpty(username, "账户不能为空");
        AssertUtils.isNotEmpty(password, "密码不能为空");
        AssertUtils.isNotEmpty(passwordAgain, "确认密码不能为空");
        AssertUtils.isTrue(password.equalsIgnoreCase(passwordAgain), "两次输入密码不一致");
        AssertUtils.isNotEmpty(authType, "注册方式不能为空");
        AssertUtils.isNotEmpty(uuid, "验证码不能为空");
        AssertUtils.isNotEmpty(code, "验证码不能为空");
    }
}
