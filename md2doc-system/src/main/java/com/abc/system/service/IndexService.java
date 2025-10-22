package com.abc.system.service;

import com.abc.system.domain.dto.CaptchaDTO;
import com.abc.system.domain.dto.EmailDTO;
import com.abc.system.domain.dto.LoginDTO;
import com.abc.system.domain.dto.RegisterDTO;
import com.abc.system.domain.vo.CaptchaVO;
import com.abc.system.domain.vo.EmailVO;
import com.abc.system.domain.vo.MenuRouterVO;

import java.util.List;

public interface IndexService {
    String login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);

    List<MenuRouterVO> getMenuRoutes();

    List<MenuRouterVO> getMenuWhiteRoutes();

    CaptchaVO getCaptchaImg(CaptchaDTO captchaDTO);

    Boolean checkCaptchaImg(String uuid, String code);

    void invalidCaptcha(String uuid);

    Boolean checkEmailCode(String emailUuid, String emailCode);

    EmailVO sendRegisterEmail(EmailDTO emailDTO);
}
