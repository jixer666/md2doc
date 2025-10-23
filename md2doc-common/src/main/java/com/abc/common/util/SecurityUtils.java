package com.abc.common.util;

import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.dialect.PropsUtil;
import com.abc.common.constant.CommonConstants;
import com.abc.common.core.threadlocal.ThreadLocalTempVar;
import com.abc.common.domain.dto.LoginUserDTO;
import com.abc.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Slf4j
public class SecurityUtils {

    public static final String DEV_ENV = "dev";

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


    public static Long getUserId() {
        try {
            return getLoginUser().getUserId();
        } catch (Exception e) {
            Object temVar = ThreadLocalTempVar.getTempUserId();
            if (Objects.nonNull(temVar)) {
                return (Long) temVar;
            }
            log.error("获取用户ID异常：{}", e.getMessage(), e);
            throw new GlobalException("获取用户ID异常");
        }
    }

    /**
     * 获取用户
     */
    public static LoginUserDTO getLoginUser() {
        try {
            return (LoginUserDTO) getAuthentication().getPrincipal();
        } catch (Exception e) {
            log.error("获取用户信息异常：{}", e.getMessage(), e);
            throw new GlobalException("获取用户信息异常");
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }



    public static String getActiveProfile() {
        // 加载配置文件
        Props props = PropsUtil.get("application.properties");
        // 读取环境配置
        String env = props.getStr("spring.profiles.active", DEV_ENV);
        return env;
    }

    public static Boolean isDevActiveProfile() {
        return getActiveProfile().equalsIgnoreCase(DEV_ENV);
    }

    public static boolean isAnonymousUser() {
        return CommonConstants.ANONYMOUS_USER.equals(getAuthentication().getPrincipal());
    }
}
