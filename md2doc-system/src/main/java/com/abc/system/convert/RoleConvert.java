package com.abc.system.convert;

import cn.hutool.core.bean.BeanUtil;
import com.abc.common.util.IdUtils;
import com.abc.common.util.SecurityUtils;
import com.abc.system.domain.dto.RoleDTO;
import com.abc.system.domain.entity.Role;
import com.abc.system.domain.vo.MenuVO;
import com.abc.system.domain.vo.RoleMenuTreeVO;

import java.util.List;


public class RoleConvert {
    
    public static Role buildDefaultRoleByRoleDTO(RoleDTO roleDTO) {
        Role role = BeanUtil.copyProperties(roleDTO, Role.class);
        role.setRoleId(IdUtils.getId());
        role.setUserId(SecurityUtils.getUserId());
        role.setCommonParams();

        return role;
    }

    public static RoleMenuTreeVO buildRoleMenuTree(List<?> menus, List<Long> menuIds) {
        return RoleMenuTreeVO.builder()
                .menus((List<MenuVO>) menus)
                .checkMenuIds(menuIds)
                .build();
    }
}
