package com.abc.biz.service.component;

import com.abc.biz.convert.PointsConvert;
import com.abc.biz.convert.PointsFlowConvert;
import com.abc.biz.domain.dto.PointsDTO;
import com.abc.biz.domain.dto.PointsFlowDTO;
import com.abc.biz.domain.entity.Points;
import com.abc.biz.domain.enums.PointsFlowStatusEnum;
import com.abc.biz.domain.enums.PointsRuleTypeEnum;
import com.abc.biz.domain.enums.PointsUserTypeEnum;
import com.abc.biz.factory.PointsAsyncTaskFactory;
import com.abc.biz.service.PointsFlowService;
import com.abc.biz.service.PointsService;
import com.abc.common.core.async.AsyncManager;
import com.abc.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Slf4j
@Component
public class PointsServiceHelper {
    
    @Autowired
    private PointsService pointsService;
    
    @Autowired
    private PointsFlowService pointsFlowService;

    /**
     * 执行需要积分扣减的业务操作
     */
    @Transactional(rollbackFor = Exception.class)
    public <T> T executeWithPoints(Long userId, PointsRuleTypeEnum pointsRule, PointsBusinessCallback<T> callback) {
        Integer points = pointsRule.getPoints();
        Integer ruleType = pointsRule.getType();

        Points userPoints = pointsService.selectByUserId(userId);

        if (Objects.isNull(userPoints)) {
            // 初始化用户积分
            PointsDTO initPointsDTO = PointsConvert.buildPointsDTOByInitPoints(userId, PointsUserTypeEnum.COMMON_USER.getType(),
                    points, pointsRule.getType());
            pointsService.initPoints(initPointsDTO);
        }

        if (userPoints.isVipUser()) {
            // vip用户直接返回结果，无需扣减积分
            return callback.execute();
        }


        // 1. 冻结积分
        pointsService.forzenPoints(userId, points);
        
        try {
            // 2. 执行业务逻辑
            T result = callback.execute();
            
            // 3. 扣减积分（冻结转已使用）
            pointsService.reducePoints(userId, points);
            
            // 4. 记录成功流水
            AsyncManager.me().execute(PointsAsyncTaskFactory.recordPointsFlowTask(userId, points, ruleType, PointsFlowStatusEnum.SUCCESS.getStatus()));

            return result;
            
        } catch (Exception e) {

            // 5. 业务异常，解冻积分
            pointsService.unfreezePoints(userId, points);
            
            // 6. 记录失败流水
            AsyncManager.me().execute(PointsAsyncTaskFactory.recordPointsFlowTask(userId, points, ruleType, PointsFlowStatusEnum.FAIL.getStatus()));

            log.error("积分业务执行失败 - 用户: {}, 积分: {}, 原因: {}", userId, points, e.getMessage(), e);
            throw new GlobalException("业务处理失败: " + e.getMessage());
        }
    }
    
    @FunctionalInterface
    public interface PointsBusinessCallback<T> {
        T execute();
    }
}