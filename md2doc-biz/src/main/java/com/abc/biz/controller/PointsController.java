package com.abc.biz.controller;

import com.abc.biz.domain.vo.PointsVO;
import com.abc.common.domain.vo.ApiResult;
import com.abc.common.domain.vo.PageResult;
import com.abc.biz.domain.dto.PointsDTO;
import com.abc.biz.service.PointsService;
import com.abc.common.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 积分控制器
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Api(tags = "积分接口")
@RestController
@RequestMapping("/biz/points")
public class PointsController {

    @Autowired
    private PointsService pointsService;

    @ApiOperation("查询积分分页")
    @GetMapping("/page")
    public ApiResult<PageResult> getPointsPage(PointsDTO pointsDTO) {
        PageResult pointsPages = pointsService.getPointsPageWithUiParam(pointsDTO);

        return ApiResult.success(pointsPages);
    }

    @ApiOperation("更新积分")
    @PutMapping
    public ApiResult<Void> updatePoints(@RequestBody PointsDTO pointsDTO) {
        pointsService.updatePoints(pointsDTO);

        return ApiResult.success();
    }

    @ApiOperation("新增积分")
    @PostMapping
    public ApiResult<Void> savePoints(@RequestBody PointsDTO pointsDTO) {
        pointsService.savePoints(pointsDTO);

        return ApiResult.success();
    }

    @ApiOperation("删除积分")
    @DeleteMapping
    public ApiResult<Void> deletePoints(@RequestBody PointsDTO pointsDTO) {
        pointsService.deletePoints(pointsDTO);

        return ApiResult.success();
    }

    @ApiOperation("查询用户积分")
    @PostMapping("/getUserPoints")
    public ApiResult<PointsVO> getUserPoints() {
        return ApiResult.success(pointsService.getUserPoints(SecurityUtils.getUserId()));
    }

}
