package com.abc.biz.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointsRuleTypeEnum {

    FREE_TRANS(1, 0, "免费转换"),
    AI_TRANS(2, 5, "AI专业转换"),
    EXPORT(3, 5, "导出"),
    NEWCOMER(4, 10, "新人注册");

    private Integer type;
    private Integer points;
    private String desc;

}
