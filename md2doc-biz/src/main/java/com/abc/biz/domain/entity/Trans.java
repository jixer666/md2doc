package com.abc.biz.domain.entity;

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
 * MD转换实体
 *
 * @author LiJunXi
 * @date 2025-10-20
 */
@Data
@Builder
@TableName("tb_trans")
@AllArgsConstructor
@NoArgsConstructor
public class Trans extends BaseEntity {

    @TableId
    @ApiModelProperty("转换ID")
    private Long transId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("MD内容")
    private String content;

    @ApiModelProperty("生成文件路径")
    private String filePath;

    @ApiModelProperty("创建时间")
    private Date createTine;


}
