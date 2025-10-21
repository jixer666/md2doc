package com.abc.biz.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointsUserTypeEnum {

    COMMON_USER(1, "普通用户"),
    VIP_USER(2, "会员用户");

    private Integer type;
    private String desc;

}
