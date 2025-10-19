package com.abc.system.convert;

import cn.hutool.core.bean.BeanUtil;
import com.abc.common.util.IdUtils;
import com.abc.common.util.SecurityUtils;
import com.abc.system.domain.dto.DictDTO;
import com.abc.system.domain.entity.Dict;

public class DictConvert {
    public static Dict buildDefaultDictByDictDTO(DictDTO dictDTO) {
        Dict dict = BeanUtil.copyProperties(dictDTO, Dict.class);
        dict.setDictId(IdUtils.getId());
        dict.setUserId(SecurityUtils.getUserId());
        dict.setCommonParams();

        return dict;
    }
}
