package com.abc.biz.convert;

import cn.hutool.core.bean.BeanUtil;
import com.abc.biz.domain.entity.Points;
import com.abc.common.util.IdUtils;
import com.abc.common.util.SecurityUtils;
import com.abc.biz.domain.dto.PointsFlowDTO;
import com.abc.biz.domain.entity.PointsFlow;

/**
 * 积分流水转换器
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
public class PointsFlowConvert {
    public static PointsFlow buildDefaultPointsFlowByPointsFlowDTO(PointsFlowDTO pointsFlowDTO) {
        PointsFlow pointsFlow = BeanUtil.copyProperties(pointsFlowDTO, PointsFlow.class);
        pointsFlow.setFlowId(IdUtils.getId());
        pointsFlow.setUserId(SecurityUtils.getUserId());
        pointsFlow.setCommonParams();

        return pointsFlow;
    }

    public static PointsFlowDTO buildPointsFlowDTO(Integer changPoints, Integer totalPoints, Integer ruleType, Integer status) {
        PointsFlowDTO pointsFlowDTO = new PointsFlowDTO();
        pointsFlowDTO.setRuleType(ruleType);
        pointsFlowDTO.setChangePoints(changPoints);
        pointsFlowDTO.setTotalPoints(totalPoints);
        pointsFlowDTO.setStatus(status);

        return pointsFlowDTO;
    }
}
