package com.abc.biz.convert;

import cn.hutool.core.bean.BeanUtil;
import com.abc.common.util.IdUtils;
import com.abc.common.util.SecurityUtils;
import com.abc.biz.domain.dto.TransDTO;
import com.abc.biz.domain.entity.Trans;

/**
 * MD转换转换器
 *
 * @author LiJunXi
 * @date 2025-10-20
 */
public class TransConvert {
    public static Trans buildDefaultTransByTransDTO(TransDTO transDTO) {
        Trans trans = BeanUtil.copyProperties(transDTO, Trans.class);
        trans.setTransId(IdUtils.getId());
        trans.setCommonParams();

        return trans;
    }
}
