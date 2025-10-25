package com.abc.common.exception;

import com.abc.common.domain.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.net.BindException;

@Slf4j
@RestControllerAdvice
public class SystemExceptionHandler {

    @ExceptionHandler(value = GlobalException.class)
    public ApiResult<Void> handleGlobalException(GlobalException e) {
        log.error("自定义出现异常: {}, ", e.getMessage(), e);
        return ApiResult.fail(e.getMessage());
    }

    @ExceptionHandler(value = InternalAuthenticationServiceException.class)
    public ApiResult<Void> handleAuthenticationServiceException(InternalAuthenticationServiceException e) {
        Throwable cause = e.getCause();
        if (cause instanceof GlobalException) {
            return ApiResult.fail(cause.getMessage());
        }

        log.error("认证服务异常: {}", e.getMessage(), e);
        return ApiResult.fail("认证服务异常");
    }

    @ExceptionHandler(value = BindException.class)
    public ApiResult<Void> handleBindException(BindException e) {
        log.error("校验出现异常: {}, ", e.getMessage(), e);
        return ApiResult.fail(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ApiResult<Void> handleException(Exception e) {
        log.error("系统出现异常: {}, ", e.getMessage(), e);
        return ApiResult.fail("系统出现异常");
    }

}
