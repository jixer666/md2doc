package com.abc.biz.factory;

import cn.hutool.extra.spring.SpringUtil;
import com.abc.biz.convert.PointsFlowConvert;
import com.abc.biz.domain.dto.PointsFlowDTO;
import com.abc.biz.domain.entity.Points;
import com.abc.biz.service.PointsFlowService;
import com.abc.biz.service.PointsService;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * 积分异步工厂（产生任务用）
 *
 * @author LiJunxi
 */
@Slf4j
public class PointsAsyncTaskFactory {

    public static TimerTask recordPointsFlowTask(Long userId, Integer points, Integer ruleType, Integer status) {
        return new TimerTask() {
            @Override
            public void run() {
                Points userPoints = SpringUtil.getBean(PointsService.class).selectByUserId(userId);
                PointsFlowDTO pointsFlowDTO = PointsFlowConvert.buildPointsFlowDTO(points, userPoints.getAvailablePoints(), ruleType, status);
                pointsFlowDTO.setUserId(userId);
                SpringUtil.getBean(PointsFlowService.class).savePointsFlow(pointsFlowDTO);
            }
        };
    }
}
