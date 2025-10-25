package com.abc.biz.domain.vo;

import java.util.Date;

import lombok.Data;

/**
 * 积分VO对象
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Data
public class PointsVO {

    private Long userId;

    private Integer availablePoints;

    private Integer totalPoints;

    private Integer frozenPoints;

    private Integer usedPoints;

    private Integer userType;

    private Date beginTime;

    private Date endTime;


}
