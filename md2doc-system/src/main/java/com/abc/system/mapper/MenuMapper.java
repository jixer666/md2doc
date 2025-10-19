package com.abc.system.mapper;

import com.abc.system.domain.dto.MenuDTO;
import com.abc.system.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> selectMenusByUserId(Long userId);

    List<Long> selectMenuIdsByRoleId(Long roleId);

    List<Menu> selectMenusByParentId(Long menuId);

    List<Menu> selectMenusByMenuType(Integer type);

    List<Menu> selectMenusByMenuDTO(MenuDTO menuDTO);
}
