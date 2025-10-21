package com.abc.biz.service;

import com.abc.common.domain.vo.PageResult;
import com.abc.biz.domain.dto.PointsFlowDTO;
import com.abc.biz.domain.entity.PointsFlow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 积分流水接口
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
public interface PointsFlowService extends IService<PointsFlow> {

    PageResult getPointsFlowPageWithUiParam(PointsFlowDTO pointsFlowDTO);

    void updatePointsFlow(PointsFlowDTO pointsFlowDTO);

    void savePointsFlow(PointsFlowDTO pointsFlowDTO);

    void deletePointsFlow(PointsFlowDTO pointsFlowDTO);
}
