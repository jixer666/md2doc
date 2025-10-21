package com.abc.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.abc.common.core.service.BaseServiceImpl;
import com.abc.common.domain.vo.PageResult;
import com.abc.common.util.AssertUtils;
import com.abc.biz.convert.PointsFlowConvert;
import com.abc.biz.domain.dto.PointsFlowDTO;
import com.abc.biz.domain.entity.PointsFlow;
import com.abc.biz.domain.vo.PointsFlowVO;
import com.abc.biz.mapper.PointsFlowMapper;
import com.abc.biz.service.PointsFlowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分流水业务处理
 *
 * @author LiJunXi
 * @date 2025-10-21
 */
@Service
public class PointsFlowServiceImpl extends BaseServiceImpl<PointsFlowMapper, PointsFlow> implements PointsFlowService {

    @Autowired
    private PointsFlowMapper pointsFlowMapper;

    @Override
    public PageResult getPointsFlowPageWithUiParam(PointsFlowDTO pointsFlowDTO) {
        startPage();
        List<PointsFlow> pointsFlows = pointsFlowMapper.selectPointsFlowList(pointsFlowDTO);
        List<PointsFlowVO> pointsFlowVOList = pageList2CustomList(pointsFlows, (List<PointsFlow> list) -> {
            return BeanUtil.copyToList(list, PointsFlowVO.class);
        });

        return buildPageResult(pointsFlowVOList);
    }

    @Override
    public void updatePointsFlow(PointsFlowDTO pointsFlowDTO) {
        pointsFlowDTO.checkUpdateParams();
        PointsFlow pointsFlow = pointsFlowMapper.selectById(pointsFlowDTO.getFlowId());
        AssertUtils.isNotEmpty(pointsFlow, "积分流水不存在");
        BeanUtils.copyProperties(pointsFlowDTO, pointsFlow);
        pointsFlowMapper.updateById(pointsFlow);
    }

    @Override
    public void savePointsFlow(PointsFlowDTO pointsFlowDTO) {
        pointsFlowDTO.checkSaveParams();
        PointsFlow pointsFlow = PointsFlowConvert.buildDefaultPointsFlowByPointsFlowDTO(pointsFlowDTO);
        pointsFlowMapper.insert(pointsFlow);
    }

    @Override
    public void deletePointsFlow(PointsFlowDTO pointsFlowDTO) {
        pointsFlowDTO.checkDeleteParams();

        pointsFlowMapper.deleteBatchIds(pointsFlowDTO.getPointsFlowIds());
    }
    

}