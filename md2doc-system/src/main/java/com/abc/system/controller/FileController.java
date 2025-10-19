package com.abc.system.controller;

import com.abc.common.domain.vo.ApiResult;
import com.abc.common.domain.vo.PageResult;
import com.abc.system.domain.dto.FileDTO;
import com.abc.system.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文件控制器
 *
 * @author LiJunXi
 * @date 2025-10-07
 */
@Api(tags = "文件接口")
@RestController
@RequestMapping("/system/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("查询文件分页")
    @GetMapping("/page")
    public ApiResult<PageResult> getFilePage(FileDTO fileDTO) {
        PageResult filePages = fileService.getFilePageWithUiParam(fileDTO);

        return ApiResult.success(filePages);
    }

    @ApiOperation("更新文件")
    @PutMapping
    public ApiResult<Void> updateFile(@RequestBody FileDTO fileDTO) {
        fileService.updateFile(fileDTO);

        return ApiResult.success();
    }

    @ApiOperation("新增文件")
    @PostMapping
    public ApiResult<Void> saveFile(@RequestBody FileDTO fileDTO) {
        fileService.saveFile(fileDTO);

        return ApiResult.success();
    }

    @ApiOperation("删除文件")
    @DeleteMapping
    public ApiResult<Void> deleteFile(@RequestBody FileDTO fileDTO) {
        fileService.deleteFile(fileDTO);

        return ApiResult.success();
    }

}
