package com.abc.biz.domain.vo;

import java.util.Date;

import lombok.Data;

/**
 * 积分流水VO对象
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Data
public class PointsFlowVO {

    private Long flowId;

    private Long userId;

    private Integer ruleType;

    private Integer changePoints;

    private Integer totalPoints;

    private Date createTime;

    private Date updateTime;

    private Integer status;

}
