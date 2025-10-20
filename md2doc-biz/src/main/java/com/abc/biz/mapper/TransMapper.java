package com.abc.biz.mapper;

import com.abc.biz.domain.dto.TransDTO;
import com.abc.biz.domain.entity.Trans;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MD转换Mapper接口
 *
 * @author LiJunXi
 * @date 2025-10-20
 */
@Mapper
public interface TransMapper extends BaseMapper<Trans> {
    List<Trans> selectTransList(TransDTO transDTO);
}
