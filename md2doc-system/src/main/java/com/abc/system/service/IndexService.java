package com.abc.system.service;

import com.abc.system.domain.dto.LoginDTO;
import com.abc.system.domain.dto.RegisterDTO;
import com.abc.system.domain.vo.MenuRouterVO;

import java.util.List;

public interface IndexService {
    String login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);

    List<MenuRouterVO> getMenuRoutes();

    List<MenuRouterVO> getMenuWhiteRoutes();
}
