package com.abc.biz.domain.dto;

import cn.hutool.core.collection.CollUtil;
import com.abc.common.util.AssertUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 积分流水DTO对象
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Data
public class PointsFlowDTO {

    private Long flowId;

    private Long userId;

    private Integer ruleType;

    private Integer changePoints;

    private Integer totalPoints;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private Integer ver;


    // 用于批量删除
    private List<Long> pointsFlowIds;

    public void checkUpdateParams() {
        AssertUtils.isNotEmpty(this, "积分流水参数不能为空");
        AssertUtils.isNotEmpty(flowId, "积分流水ID不能为空");
    }

    public void checkSaveParams() {
        AssertUtils.isNotEmpty(this, "积分流水参数不能为空");
    }

    public void checkDeleteParams() {
        AssertUtils.isNotEmpty(this, "积分流水参数不能为空");
        AssertUtils.isTrue(CollUtil.isNotEmpty(pointsFlowIds), "积分流水ID列表不能为空");
    }
}
