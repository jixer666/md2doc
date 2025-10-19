package com.abc.system.service;

import com.abc.common.domain.vo.PageResult;
import com.abc.system.domain.dto.DictDataDTO;
import com.abc.system.domain.entity.DictData;
import com.abc.system.domain.vo.DictDataVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DictDataService extends IService<DictData> {

    PageResult getDictDataPageWithUiParam(DictDataDTO dictDataDTO);

    void updateDictData(DictDataDTO dictDataDTO);

    void saveDictData(DictDataDTO dictDataDTO);

    void deleteDictData(DictDataDTO dictDataDTO);

    List<DictDataVO> getDictDataByDictKey(String dictKey);
}
