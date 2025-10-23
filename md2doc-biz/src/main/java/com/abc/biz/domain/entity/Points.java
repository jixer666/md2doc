package com.abc.biz.domain.entity;

import com.abc.biz.domain.enums.PointsUserTypeEnum;
import com.abc.common.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 积分实体
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Data
@Builder
@TableName("tb_points")
@AllArgsConstructor
@NoArgsConstructor
public class Points {

    @TableId
    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("积累总积分")
    private Integer totalPoints;

    @ApiModelProperty("可用积分")
    private Integer availablePoints;

    @ApiModelProperty("冻结积分")
    private Integer frozenPoints ;

    @ApiModelProperty("已使用积分")
    private Integer usedPoints;

    @ApiModelProperty("用户类型")
    private Integer userType;

    @ApiModelProperty("会员开始时间")
    private Date beginTime;

    @ApiModelProperty("会员结束时间")
    private Date endTime;

    @ApiModelProperty("版本号")
    private Integer ver;


    public boolean isVipUser() {
        Date nowDate = new Date();
        return PointsUserTypeEnum.VIP_USER.getType().equals(userType) && nowDate.before(endTime) && nowDate.after(beginTime);
    }

    public boolean isCommonUser() {
        return PointsUserTypeEnum.COMMON_USER.getType().equals(userType);
    }
}
