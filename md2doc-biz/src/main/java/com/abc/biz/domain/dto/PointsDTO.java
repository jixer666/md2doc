package com.abc.biz.domain.dto;

import cn.hutool.core.collection.CollUtil;
import com.abc.common.util.AssertUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 积分DTO对象
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Data
public class PointsDTO {

    private Long userId;

    private Integer points;

    private Integer userType;

    private Date beginTime;

    private Date endTime;


    // 用于批量删除
    private List<Long> pointsIds;

    // 用于初始化积分
    private Integer ruleType;


    public void checkUpdateParams() {
        AssertUtils.isNotEmpty(this, "积分参数不能为空");
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");
    }

    public void checkSaveParams() {
        AssertUtils.isNotEmpty(this, "积分参数不能为空");
    }

    public void checkDeleteParams() {
        AssertUtils.isNotEmpty(this, "积分参数不能为空");
        AssertUtils.isTrue(CollUtil.isNotEmpty(pointsIds), "积分ID列表不能为空");
    }

    public void checkInitParams() {
        AssertUtils.isNotEmpty(this, "积分参数不能为空");
        AssertUtils.isNotEmpty(points, "积分不能为空");
        AssertUtils.isNotEmpty(userType, "用户类型不能为空");
    }
}
