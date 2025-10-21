package com.abc.biz.domain.entity;

import com.abc.common.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 积分流水实体
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Data
@Builder
@TableName("tb_points_flow")
@AllArgsConstructor
@NoArgsConstructor
public class PointsFlow extends BaseEntity {

    @TableId
    @ApiModelProperty("流水ID")
    private Long flowId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("积分规则类型")
    private Integer ruleType;

    @ApiModelProperty("变化积分")
    private Integer changePoints;

    @ApiModelProperty("变化后积分")
    private Integer totalPoints;


}
