package com.abc.system.service;

import com.abc.common.domain.vo.PageResult;
import com.abc.system.domain.dto.DictDTO;
import com.abc.system.domain.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DictService extends IService<Dict> {

    PageResult getDictPageWithUiParam(DictDTO dictDTO);

    void updateDict(DictDTO dictDTO);

    void saveDict(DictDTO dictDTO);

    void deleteDict(DictDTO dictDTO);
}
