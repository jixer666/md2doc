package com.abc.biz.domain.dto;

import cn.hutool.core.collection.CollUtil;
import com.abc.common.util.AssertUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * MD转换DTO对象
 *
 * @author LiJunXi
 * @date 2025-10-20
 */
@Data
public class TransDTO {

    private Long transId;

    private Long userId;

    private String preContent;

    private String transContent;

    // 用于批量删除
    private List<Long> transIds;

    public void checkUpdateParams() {
        AssertUtils.isNotEmpty(this, "MD转换参数不能为空");
        AssertUtils.isNotEmpty(transId, "MD转换ID不能为空");
    }

    public void checkSaveParams() {
        AssertUtils.isNotEmpty(this, "MD转换参数不能为空");
    }

    public void checkDeleteParams() {
        AssertUtils.isNotEmpty(this, "MD转换参数不能为空");
        AssertUtils.isTrue(CollUtil.isNotEmpty(transIds), "MD转换ID列表不能为空");
    }

    public void checkPreviewParams() {
        AssertUtils.isNotEmpty(this, "转换参数不能为空");
        AssertUtils.isNotEmpty(preContent, "转换内容不能为空");
    }

    public void checkExportParams() {
        AssertUtils.isNotEmpty(this, "转换参数不能为空");
        if (Objects.isNull(transId)) {
            AssertUtils.isNotEmpty(preContent, "转换内容不能为空");
        }
    }

}
