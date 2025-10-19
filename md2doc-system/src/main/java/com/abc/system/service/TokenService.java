package com.abc.system.service;

import com.abc.common.domain.dto.LoginUserDTO;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    String createToken(LoginUserDTO loginUser);

    void refreshToken(LoginUserDTO loginUser);

    void validateToken(LoginUserDTO loginUser);

    String getToken(HttpServletRequest request);

    LoginUserDTO getLoginUserDTO(String token);

    void invalidToken(String token);
}
