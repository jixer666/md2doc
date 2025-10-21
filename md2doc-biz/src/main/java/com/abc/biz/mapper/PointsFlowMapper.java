package com.abc.biz.mapper;

import com.abc.biz.domain.dto.PointsFlowDTO;
import com.abc.biz.domain.entity.PointsFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 积分流水Mapper接口
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Mapper
public interface PointsFlowMapper extends BaseMapper<PointsFlow> {
    List<PointsFlow> selectPointsFlowList(PointsFlowDTO pointsFlowDTO);
}
