package com.abc.system.service;

import com.abc.common.domain.vo.PageResult;
import com.abc.system.domain.dto.GenerateTableColumnDTO;
import com.abc.system.domain.entity.GenerateTableColumn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface GenerateTableColumnService extends IService<GenerateTableColumn> {

    PageResult getGenerateTableColumnPageWithUiParam(GenerateTableColumnDTO menuDTO);

    void updateGenerateTableColumn(GenerateTableColumnDTO menuDTO);

    void saveGenerateTableColumn(GenerateTableColumnDTO menuDTO);

    void deleteGenerateTableColumn(GenerateTableColumnDTO menuDTO);

    void importTableColumn(String tableName, Long generateTableId);

    void updateGenerateTableColumns(List<GenerateTableColumn> tableColumns);

    void deleteGenerateTableColumnByGenTableIds(List<Long> genTableIds);
}
