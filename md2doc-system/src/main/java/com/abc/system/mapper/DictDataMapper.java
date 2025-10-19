package com.abc.system.mapper;

import com.abc.system.domain.dto.DictDataDTO;
import com.abc.system.domain.entity.DictData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {
    List<DictData> selectDictDataList(DictDataDTO dictDataDTO);

    List<DictData> selectDictDataByDictKey(String dictKey);
}
