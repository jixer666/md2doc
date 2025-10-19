package com.abc.system.convert;

import cn.hutool.core.bean.BeanUtil;
import com.abc.common.util.IdUtils;
import com.abc.common.util.SecurityUtils;
import com.abc.system.domain.dto.DictDataDTO;
import com.abc.system.domain.entity.DictData;
import com.abc.system.domain.vo.DictDataVO;

import java.util.List;

public class DictDataConvert {
    public static DictData buildDefaultDictDataByDictDataDTO(DictDataDTO dictDataDTO) {
        DictData dictData = BeanUtil.copyProperties(dictDataDTO, DictData.class);
        dictData.setDictDataId(IdUtils.getId());
        dictData.setUserId(SecurityUtils.getUserId());
        dictData.setCommonParams();

        return dictData;
    }

    public static List<DictDataVO> converDictDataVoListByDictDataList(List<DictData> dictDataList) {
        return BeanUtil.copyToList(dictDataList, DictDataVO.class);
    }

}
