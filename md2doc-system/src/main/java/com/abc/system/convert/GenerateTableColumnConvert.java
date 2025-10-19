package com.abc.system.convert;

import com.abc.system.util.GenerateUtils;
import com.abc.common.util.IdUtils;
import com.abc.common.util.StringUtils;
import com.abc.system.domain.dto.GenerateTableColumnDTO;
import com.abc.system.domain.entity.GenerateTableColumn;

public class GenerateTableColumnConvert {

    public static GenerateTableColumn buildDefaultGenerateTableColumnByGenerateTableColumnDTO(GenerateTableColumnDTO generateTableColumnDTO) {
        return null;
    }

    public static void initDefaultGenerateTableColumn(GenerateTableColumn generateTableColumn) {
        generateTableColumn.setGenTableColumnId(IdUtils.getId());
        generateTableColumn.setColumnType(GenerateUtils.getColumnType(generateTableColumn.getColumnType()));
        generateTableColumn.setJavaField(StringUtils.toCamelCase(generateTableColumn.getColumnName()));
        generateTableColumn.setJavaType(GenerateUtils.getJavaType(generateTableColumn.getColumnType()));
        generateTableColumn.setCommonParams();
    }
}
