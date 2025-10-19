package com.abc.system.controller;

import com.abc.common.domain.vo.ApiResult;
import com.abc.common.domain.vo.PageResult;
import com.abc.system.domain.dto.DictDTO;
import com.abc.system.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "字典接口")
@RestController
@RequestMapping("/system/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @ApiOperation("查询字典分页")
    @GetMapping("/page")
    public ApiResult<PageResult> getDictPage(DictDTO dictDTO) {
        PageResult dictPages = dictService.getDictPageWithUiParam(dictDTO);

        return ApiResult.success(dictPages);
    }

    @ApiOperation("更新字典")
    @PutMapping
    public ApiResult<Void> updateDict(@RequestBody DictDTO dictDTO) {
        dictService.updateDict(dictDTO);

        return ApiResult.success();
    }

    @ApiOperation("新增字典")
    @PostMapping
    public ApiResult<Void> saveDict(@RequestBody DictDTO dictDTO) {
        dictService.saveDict(dictDTO);

        return ApiResult.success();
    }

    @ApiOperation("删除字典")
    @DeleteMapping
    public ApiResult<Void> deleteDict(@RequestBody DictDTO dictDTO) {
        dictService.deleteDict(dictDTO);

        return ApiResult.success();
    }


}
