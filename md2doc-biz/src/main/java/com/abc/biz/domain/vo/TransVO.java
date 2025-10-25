package com.abc.biz.domain.vo;

import java.util.Date;

import lombok.Data;

/**
 * MD转换VO对象
 *
 * @author LiJunXi
 * @date 2025-10-20
 */
@Data
public class TransVO {

    private Long transId;

    private Long userId;

    private String preContent;

    private String transContent;

    private Date createTime;

    private Date updateTime;

    private Integer status;

}
