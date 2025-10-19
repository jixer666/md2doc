package com.abc.system.service.impl;

import com.abc.common.constant.MenuConstants;
import com.abc.common.util.SecurityUtils;
import com.abc.system.convert.MenuConvert;
import com.abc.system.domain.dto.LoginDTO;
import com.abc.common.domain.dto.LoginUserDTO;
import com.abc.system.domain.dto.RegisterDTO;
import com.abc.system.domain.entity.Menu;
import com.abc.system.domain.enums.MenuTypeEnum;
import com.abc.system.domain.vo.MenuRouterVO;
import com.abc.system.factory.LoginStrategyFactory;
import com.abc.system.service.IndexService;
import com.abc.system.service.MenuService;
import com.abc.system.service.TokenService;
import com.abc.system.strategy.login.AuthStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MenuService menuService;

    @Override
    public String login(LoginDTO loginDTO) {
        AuthStrategy authStrategy = LoginStrategyFactory.getAuthStrategy(loginDTO.getAuthType());
        LoginUserDTO loginUser = authStrategy.authenticate(loginDTO);
        return tokenService.createToken(loginUser);
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        AuthStrategy authStrategy = LoginStrategyFactory.getAuthStrategy(registerDTO.getAuthType());
        authStrategy.doRegister(registerDTO);
    }

    @Override
    public List<MenuRouterVO> getMenuRoutes() {
        List<Menu> menus = menuService.getMenusByUserId(SecurityUtils.getUserId());

        return buildMenuRouter(MenuConstants.DEFAULT_MENU_PARENT_ID, menus);
    }

    private List<MenuRouterVO> buildMenuRouter(Long menuId, List<Menu> menus) {
        List<MenuRouterVO> routers = new ArrayList<>();

        List<Menu> filterMenus = menus.stream().filter(item -> item.getParentId().equals(menuId)).sorted(new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return o1.getOrderNum() - o2.getOrderNum();
            }
        }).collect(Collectors.toList());

        for (Menu menu : filterMenus) {
            if (menu.getParentId().equals(menuId)) {
                List<MenuRouterVO> children = buildMenuRouter(menu.getMenuId(), menus);
                routers.add(MenuConvert.convertToMenuRouterVO(menu, children));
            }
        }

        return routers;
    }

    @Override
    public List<MenuRouterVO> getMenuWhiteRoutes() {
        List<Menu> menus = menuService.getMenusByMenuType(MenuTypeEnum.FRONT.getType());

        return buildMenuRouter(MenuConstants.DEFAULT_MENU_PARENT_ID, menus);
    }
}
