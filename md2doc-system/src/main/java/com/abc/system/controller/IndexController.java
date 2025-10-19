package com.abc.system.controller;

import com.abc.common.domain.vo.ApiResult;
import com.abc.system.domain.dto.LoginDTO;
import com.abc.system.domain.dto.RegisterDTO;
import com.abc.system.domain.vo.MenuRouterVO;
import com.abc.system.service.IndexService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system")
public class IndexController {

    @Autowired
    private IndexService indexService;


    @ApiOperation("登录")
    @PostMapping("/login")
    public ApiResult<String> login(@RequestBody LoginDTO loginDTO) {
        String token = indexService.login(loginDTO);

        return ApiResult.success(token);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public ApiResult<Void> register(@RequestBody RegisterDTO registerDTO) {
        indexService.register(registerDTO);

        return ApiResult.success();
    }

    @ApiOperation("获取菜单权限")
    @GetMapping("/routes")
    public ApiResult<List<MenuRouterVO>> getMenuRoutes() {
        List<MenuRouterVO> routers = indexService.getMenuRoutes();

        return ApiResult.success(routers);
    }

    @ApiOperation("获取白名单菜单权限")
    @GetMapping("/white/routes")
    public ApiResult<List<MenuRouterVO>> getMenuWhiteRoutes() {
        List<MenuRouterVO> routers = indexService.getMenuWhiteRoutes();

        return ApiResult.success(routers);
    }

}
