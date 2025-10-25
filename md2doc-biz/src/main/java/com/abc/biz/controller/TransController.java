package com.abc.biz.controller;

import com.abc.biz.constant.BizConstants;
import com.abc.biz.domain.vo.TransVO;
import com.abc.common.domain.vo.ApiResult;
import com.abc.common.domain.vo.PageResult;
import com.abc.biz.domain.dto.TransDTO;
import com.abc.biz.service.TransService;
import com.abc.common.util.AssertUtils;
import com.abc.common.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * MD转换控制器
 *
 * @author LiJunXi
 * @date 2025-10-20
 */
@Api(tags = "MD转换接口")
@RestController
@RequestMapping("/biz/trans")
public class TransController {

    @Autowired
    private TransService transService;

    @ApiOperation("查询MD转换分页")
    @GetMapping("/page")
    public ApiResult<PageResult> getTransPage(TransDTO transDTO) {
        AssertUtils.isTrue(SecurityUtils.getLoginUser().getUsername().equals(BizConstants.ADMIN_ACCOUNT), "无权限");
        PageResult transPages = transService.getTransPageWithUiParam(transDTO);

        return ApiResult.success(transPages);
    }

    @ApiOperation("更新MD转换")
    @PutMapping
    public ApiResult<Void> updateTrans(@RequestBody TransDTO transDTO) {
        AssertUtils.isTrue(SecurityUtils.getLoginUser().getUsername().equals(BizConstants.ADMIN_ACCOUNT), "无权限");
        transService.updateTrans(transDTO);

        return ApiResult.success();
    }

    @ApiOperation("新增MD转换")
    @PostMapping
    public ApiResult<Void> saveTrans(@RequestBody TransDTO transDTO) {
        AssertUtils.isTrue(SecurityUtils.getLoginUser().getUsername().equals(BizConstants.ADMIN_ACCOUNT), "无权限");
        transService.saveTrans(transDTO);

        return ApiResult.success();
    }

    @ApiOperation("删除MD转换")
    @DeleteMapping
    public ApiResult<Void> deleteTrans(@RequestBody TransDTO transDTO) {
        AssertUtils.isTrue(SecurityUtils.getLoginUser().getUsername().equals(BizConstants.ADMIN_ACCOUNT), "无权限");
        transService.deleteTrans(transDTO);

        return ApiResult.success();
    }

    @ApiOperation("预览")
    @PostMapping("/preview")
    public ApiResult<TransVO> previewTransMd(@RequestBody TransDTO transDTO) {
        TransVO transVO = transService.previewTransMd(transDTO);

        return ApiResult.success(transVO);
    }

    @ApiOperation("AI专业转换")
    @PostMapping("/ai/preview")
    public ApiResult<TransVO> previewTransMdByAi(@RequestBody TransDTO transDTO) {
        TransVO transVO = transService.previewTransMdByAi(transDTO);

        return ApiResult.success(transVO);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public ResponseEntity<byte[]> exportTransMd(@RequestBody TransDTO transDTO) {
        return transService.exportTransMd(transDTO);
    }

    @ApiOperation("查询用户MD转换分页")
    @GetMapping("/getUserTransPage")
    public ApiResult<PageResult> getUserTransPage(TransDTO transDTO) {
        transDTO.setUserId(SecurityUtils.getUserId());
        PageResult transPages = transService.getTransPageWithUiParam(transDTO);

        return ApiResult.success(transPages);
    }

}
