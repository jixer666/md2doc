package com.abc.biz.controller;

import com.abc.biz.constant.BizConstants;
import com.abc.common.domain.vo.ApiResult;
import com.abc.common.domain.vo.PageResult;
import com.abc.biz.domain.dto.PointsFlowDTO;
import com.abc.biz.service.PointsFlowService;
import com.abc.common.util.AssertUtils;
import com.abc.common.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 积分流水控制器
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Api(tags = "积分流水接口")
@RestController
@RequestMapping("/biz/pointsFlow")
public class PointsFlowController {

    @Autowired
    private PointsFlowService pointsFlowService;

    @ApiOperation("查询积分流水分页")
    @GetMapping("/page")
    public ApiResult<PageResult> getPointsFlowPage(PointsFlowDTO pointsFlowDTO) {
        AssertUtils.isTrue(SecurityUtils.getLoginUser().getUsername().equals(BizConstants.ADMIN_ACCOUNT), "无权限");
        PageResult pointsFlowPages = pointsFlowService.getPointsFlowPageWithUiParam(pointsFlowDTO);

        return ApiResult.success(pointsFlowPages);
    }

    @ApiOperation("更新积分流水")
    @PutMapping
    public ApiResult<Void> updatePointsFlow(@RequestBody PointsFlowDTO pointsFlowDTO) {
        AssertUtils.isTrue(SecurityUtils.getLoginUser().getUsername().equals(BizConstants.ADMIN_ACCOUNT), "无权限");
        pointsFlowService.updatePointsFlow(pointsFlowDTO);

        return ApiResult.success();
    }

    @ApiOperation("新增积分流水")
    @PostMapping
    public ApiResult<Void> savePointsFlow(@RequestBody PointsFlowDTO pointsFlowDTO) {
        AssertUtils.isTrue(SecurityUtils.getLoginUser().getUsername().equals(BizConstants.ADMIN_ACCOUNT), "无权限");
        pointsFlowService.savePointsFlow(pointsFlowDTO);

        return ApiResult.success();
    }

    @ApiOperation("删除积分流水")
    @DeleteMapping
    public ApiResult<Void> deletePointsFlow(@RequestBody PointsFlowDTO pointsFlowDTO) {
        AssertUtils.isTrue(SecurityUtils.getLoginUser().getUsername().equals(BizConstants.ADMIN_ACCOUNT), "无权限");
        pointsFlowService.deletePointsFlow(pointsFlowDTO);

        return ApiResult.success();
    }

    @ApiOperation("查询积分流水分页")
    @GetMapping("/getUserPointsFlowPage")
    public ApiResult<PageResult> getUserPointsFlowPage(PointsFlowDTO pointsFlowDTO) {
        pointsFlowDTO.setUserId(SecurityUtils.getUserId());
        PageResult pointsFlowPages = pointsFlowService.getPointsFlowPageWithUiParam(pointsFlowDTO);

        return ApiResult.success(pointsFlowPages);
    }

}
