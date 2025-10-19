package com.abc.system.controller;

import com.abc.common.domain.vo.ApiResult;
import com.abc.common.domain.vo.PageResult;
import com.abc.system.domain.dto.GenerateTableColumnDTO;
import com.abc.system.service.GenerateTableColumnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "代码生成表接口")
@RestController
@RequestMapping("/system/generateTableColumn")
public class GenerateTableColumnController {

    @Autowired
    private GenerateTableColumnService generateTableColumnService;

    @ApiOperation("查询代码生成表分页")
    @GetMapping("/page")
    public ApiResult<PageResult> getGenerateTableColumnPage(GenerateTableColumnDTO generateTableColumnDTO) {
        PageResult generateTableColumnPages = generateTableColumnService.getGenerateTableColumnPageWithUiParam(generateTableColumnDTO);

        return ApiResult.success(generateTableColumnPages);
    }

    @ApiOperation("更新代码生成表")
    @PutMapping
    public ApiResult<Void> updateGenerateTableColumn(@RequestBody GenerateTableColumnDTO generateTableColumnDTO) {
        generateTableColumnService.updateGenerateTableColumn(generateTableColumnDTO);

        return ApiResult.success();
    }

    @ApiOperation("新增代码生成表")
    @PostMapping
    public ApiResult<Void> saveGenerateTableColumn(@RequestBody GenerateTableColumnDTO generateTableColumnDTO) {
        generateTableColumnService.saveGenerateTableColumn(generateTableColumnDTO);

        return ApiResult.success();
    }

    @ApiOperation("删除代码生成表")
    @DeleteMapping
    public ApiResult<Void> deleteGenerateTableColumn(@RequestBody GenerateTableColumnDTO generateTableColumnDTO) {
        generateTableColumnService.deleteGenerateTableColumn(generateTableColumnDTO);

        return ApiResult.success();
    }


}
