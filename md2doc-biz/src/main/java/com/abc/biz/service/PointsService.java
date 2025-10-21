package com.abc.biz.service;

import com.abc.common.domain.vo.PageResult;
import com.abc.biz.domain.dto.PointsDTO;
import com.abc.biz.domain.entity.Points;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 积分接口
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
public interface PointsService extends IService<Points> {

    PageResult getPointsPageWithUiParam(PointsDTO pointsDTO);

    void updatePoints(PointsDTO pointsDTO);

    void savePoints(PointsDTO pointsDTO);

    void deletePoints(PointsDTO pointsDTO);

    void forzenPoints(Long userId, Integer points);

    void reducePoints(Long userId, Integer points);

    void increasePoints(Long userId, Integer points);

    void unfreezePoints(Long userId, Integer points);

    Points selectByUserId(Long userId);

    void initPoints(PointsDTO initPointsDTO);
}
