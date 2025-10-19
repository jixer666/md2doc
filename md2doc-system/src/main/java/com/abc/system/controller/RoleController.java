package com.abc.system.controller;

import com.abc.common.domain.vo.ApiResult;
import com.abc.common.domain.vo.PageResult;
import com.abc.system.domain.dto.RoleDTO;
import com.abc.system.domain.vo.RoleMenuTreeVO;
import com.abc.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("查询角色分页")
    @GetMapping("/page")
    public ApiResult<PageResult> getRolePage(RoleDTO roleDTO) {
        PageResult rolePages = roleService.getRolePageWithUiParam(roleDTO);

        return ApiResult.success(rolePages);
    }

    @ApiOperation("更新角色")
    @PutMapping
    public ApiResult<Void> updateRole(@RequestBody RoleDTO roleDTO) {
        roleService.updateRole(roleDTO);

        return ApiResult.success();
    }

    @ApiOperation("新增角色")
    @PostMapping
    public ApiResult<Void> saveRole(@RequestBody RoleDTO roleDTO) {
        roleService.saveRole(roleDTO);

        return ApiResult.success();
    }

    @ApiOperation("获取角色树型菜单权限")
    @GetMapping("/getRoleMenuTree/{roleId}")
    public ApiResult<RoleMenuTreeVO> getRoleMenuTree(@PathVariable Long roleId) {
        RoleMenuTreeVO roleMenuTreeVO = roleService.getRoleMenuTree(roleId);

        return ApiResult.success(roleMenuTreeVO);
    }

    @ApiOperation("分配菜单权限")
    @PostMapping("/saveRoleMenu")
    public ApiResult<Void> saveRoleMenu(@RequestBody RoleDTO roleDTO) {
        roleService.saveRoleMenu(roleDTO);

        return ApiResult.success();
    }

    @ApiOperation("删除角色")
    @DeleteMapping
    public ApiResult<Void> deleteRole(@RequestBody RoleDTO roleDTO) {
        roleService.deleteRole(roleDTO);

        return ApiResult.success();
    }
}
