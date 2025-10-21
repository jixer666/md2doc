package com.abc.biz.mapper;

import com.abc.biz.domain.dto.PointsDTO;
import com.abc.biz.domain.entity.Points;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 积分Mapper接口
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Mapper
public interface PointsMapper extends BaseMapper<Points> {
    List<Points> selectPointsList(PointsDTO pointsDTO);

    int forzenPointsByUserId(@Param("userId") Long userId, @Param("points") Integer points);

    int reducePointsByUserId(@Param("userId") Long userId, @Param("points") Integer points);

    int unfreezePoints(@Param("userId") Long userId, @Param("points") Integer points);

    int increasePointsByUserId(@Param("userId") Long userId, @Param("points") Integer points);

    Points selectByUserId(@Param("userId") Long userId);

}
