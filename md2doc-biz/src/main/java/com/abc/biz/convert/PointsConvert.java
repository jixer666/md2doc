package com.abc.biz.convert;

import cn.hutool.core.bean.BeanUtil;
import com.abc.biz.domain.dto.PointsDTO;
import com.abc.biz.domain.entity.Points;
import com.abc.biz.domain.vo.PointsVO;

/**
 * 积分转换器
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
public class PointsConvert {
    public static Points buildDefaultPointsByPointsDTO(PointsDTO pointsDTO) {
        Points points = BeanUtil.copyProperties(pointsDTO, Points.class);
        points.setCommonParams();

        return points;
    }

    public static PointsDTO buildPointsDTOByInitPoints(Long userId, Integer userType, Integer points, Integer ruleType) {
        PointsDTO pointsDTO = new PointsDTO();
        pointsDTO.setUserId(userId);
        pointsDTO.setUserType(userType);
        pointsDTO.setPoints(points);
        pointsDTO.setRuleType(ruleType);

        return pointsDTO;
    }

    public static PointsVO buildPointsVOByPoints(Points points) {
        return BeanUtil.copyProperties(points, PointsVO.class);
    }
}
