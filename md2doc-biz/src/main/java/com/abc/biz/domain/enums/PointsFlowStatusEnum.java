package com.abc.biz.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointsFlowStatusEnum {

    SUCCESS(1, "成功"),
    FAIL(1, "失败");

    private Integer status;
    private String desc;

}
