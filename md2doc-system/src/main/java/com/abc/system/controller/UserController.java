package com.abc.system.controller;

import com.abc.common.domain.vo.ApiResult;
import com.abc.common.domain.vo.PageResult;
import com.abc.common.util.SecurityUtils;
import com.abc.system.domain.dto.RoleDTO;
import com.abc.system.domain.dto.UserDTO;
import com.abc.system.domain.dto.UserResetPwdDTO;
import com.abc.system.domain.vo.UserRoleVO;
import com.abc.system.domain.vo.UserVO;
import com.abc.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation("查询用户信息")
    @GetMapping("/info")
    public ApiResult<UserVO> getUserInfo() {
        UserVO userVO = userService.getUserInfoWithUiParam(SecurityUtils.getUserId());

        return ApiResult.success(userVO);
    }

    @ApiOperation("查询用户分页")
    @GetMapping("/page")
    public ApiResult<PageResult> getUserPage(UserDTO userDTO) {
        PageResult pageResult = userService.getUserPageWithUiParam(userDTO);

        return ApiResult.success(pageResult);
    }

    @ApiOperation("查询用户角色")
    @GetMapping("/getUserRole/{userId}")
    public ApiResult<UserRoleVO> getUserRole(@PathVariable Long userId) {
        UserRoleVO userRole = userService.getUserRole(userId);

        return ApiResult.success(userRole);
    }

    @ApiOperation("分配用户角色")
    @PostMapping("/saveUserRole")
    public ApiResult<Void> saveUserRole(@RequestBody UserDTO userDTO) {
        userService.saveUserRole(userDTO);

        return ApiResult.success();
    }

    @ApiOperation("重置用户密码")
    @PutMapping("/resetPassword")
    public ApiResult<Void> resetPassword(@RequestBody UserResetPwdDTO userResetPwdDTO) {
        userService.resetPassword(userResetPwdDTO);

        return ApiResult.success();
    }

    @ApiOperation("更新用户")
    @PutMapping
    public ApiResult<Void> updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);

        return ApiResult.success();
    }



}
