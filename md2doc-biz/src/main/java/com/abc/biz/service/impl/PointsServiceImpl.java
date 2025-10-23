package com.abc.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.abc.biz.convert.PointsFlowConvert;
import com.abc.biz.domain.dto.PointsFlowDTO;
import com.abc.biz.domain.enums.PointsFlowStatusEnum;
import com.abc.biz.service.PointsFlowService;
import com.abc.common.core.service.BaseServiceImpl;
import com.abc.common.domain.vo.PageResult;
import com.abc.common.util.AssertUtils;
import com.abc.biz.convert.PointsConvert;
import com.abc.biz.domain.dto.PointsDTO;
import com.abc.biz.domain.entity.Points;
import com.abc.biz.domain.vo.PointsVO;
import com.abc.biz.mapper.PointsMapper;
import com.abc.biz.service.PointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分业务处理
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Slf4j
@Service
public class PointsServiceImpl extends BaseServiceImpl<PointsMapper, Points> implements PointsService {

    @Autowired
    private PointsMapper pointsMapper;

    @Autowired
    private PointsFlowService pointsFlowService;

    @Override
    public PageResult getPointsPageWithUiParam(PointsDTO pointsDTO) {
        startPage();
        List<Points> pointss = pointsMapper.selectPointsList(pointsDTO);
        List<PointsVO> pointsVOList = pageList2CustomList(pointss, (List<Points> list) -> {
            return BeanUtil.copyToList(list, PointsVO.class);
        });

        return buildPageResult(pointsVOList);
    }

    @Override
    public void updatePoints(PointsDTO pointsDTO) {
        pointsDTO.checkUpdateParams();
        Points points = pointsMapper.selectById(pointsDTO.getUserId());
        AssertUtils.isNotEmpty(points, "积分不存在");
        BeanUtils.copyProperties(pointsDTO, points);
        pointsMapper.updateById(points);
    }

    @Override
    public void savePoints(PointsDTO pointsDTO) {
        pointsDTO.checkSaveParams();
        Points points = PointsConvert.buildDefaultPointsByPointsDTO(pointsDTO);
        pointsMapper.insert(points);
    }

    @Override
    public void deletePoints(PointsDTO pointsDTO) {
        pointsDTO.checkDeleteParams();

        pointsMapper.deleteBatchIds(pointsDTO.getPointsIds());
    }

    @Override
    public void forzenPoints(Long userId, Integer points) {
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");
        AssertUtils.isNotEmpty(points, "积分不能为空");

        int rows = pointsMapper.forzenPointsByUserId(userId, points);
        AssertUtils.isTrue(rows != 0, "积分冻结失败：可用积分不足或用户不存在");
    }

    @Override
    public void unfreezePoints(Long userId, Integer points) {
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");
        AssertUtils.isNotEmpty(points, "积分不能为空");

        int rows = pointsMapper.unfreezePoints(userId, points);
        if (rows == 0) {
            log.warn("积分解冻失败：用户{}冻结积分不足{}", userId, points);
        }
    }

    @Override
    public void reducePoints(Long userId, Integer points) {
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");
        AssertUtils.isNotEmpty(points, "积分不能为空");

        int rows = pointsMapper.reducePointsByUserId(userId, points);
        AssertUtils.isTrue(rows != 0, "积分扣减失败：冻结积分不足");
    }

    @Override
    public void increasePoints(Long userId, Integer points) {
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");
        AssertUtils.isNotEmpty(points, "积分不能为空");

        int rows = pointsMapper.increasePointsByUserId(userId, points);
        AssertUtils.isTrue(rows != 0, "积分增加失败");
    }

    @Override
    public Points selectByUserId(Long userId) {
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");

        return pointsMapper.selectByUserId(userId);
    }

    @Override
    public void initPoints(PointsDTO pointsDTO) {
        pointsDTO.checkInitParams();

        savePoints(pointsDTO);
        increasePoints(pointsDTO.getUserId(), pointsDTO.getPoints());

        PointsFlowDTO pointsFlowDTO = PointsFlowConvert.buildPointsFlowDTO(pointsDTO.getPoints(), pointsDTO.getPoints(),
                pointsDTO.getRuleType(), PointsFlowStatusEnum.SUCCESS.getStatus());
        pointsFlowService.savePointsFlow(pointsFlowDTO);
    }

    @Override
    public PointsVO getUserPoints(Long userId) {
        AssertUtils.isNotEmpty(userId, "用户ID不能为空");

        Points points = selectByUserId(userId);

        return PointsConvert.buildPointsVOByPoints(points);
    }
}