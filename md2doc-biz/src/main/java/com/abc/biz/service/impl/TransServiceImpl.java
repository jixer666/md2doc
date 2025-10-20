package com.abc.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.abc.biz.util.MarkdownLatexCleaner;
import com.abc.common.core.service.BaseServiceImpl;
import com.abc.common.domain.vo.PageResult;
import com.abc.common.util.AssertUtils;
import com.abc.biz.convert.TransConvert;
import com.abc.biz.domain.dto.TransDTO;
import com.abc.biz.domain.entity.Trans;
import com.abc.biz.domain.vo.TransVO;
import com.abc.biz.mapper.TransMapper;
import com.abc.biz.service.TransService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MD转换业务处理
 *
 * @author LiJunXi
 * @date 2025-10-20
 */
@Service
public class TransServiceImpl extends BaseServiceImpl<TransMapper, Trans> implements TransService {

    @Autowired
    private TransMapper transMapper;

    @Override
    public PageResult getTransPageWithUiParam(TransDTO transDTO) {
        startPage();
        List<Trans> transs = transMapper.selectTransList(transDTO);
        List<TransVO> transVOList = pageList2CustomList(transs, (List<Trans> list) -> {
            return BeanUtil.copyToList(list, TransVO.class);
        });

        return buildPageResult(transVOList);
    }

    @Override
    public void updateTrans(TransDTO transDTO) {
        transDTO.checkUpdateParams();
        Trans trans = transMapper.selectById(transDTO.getTransId());
        AssertUtils.isNotEmpty(trans, "MD转换不存在");
        BeanUtils.copyProperties(transDTO, trans);
        transMapper.updateById(trans);
    }

    @Override
    public void saveTrans(TransDTO transDTO) {
        transDTO.checkSaveParams();
        Trans trans = TransConvert.buildDefaultTransByTransDTO(transDTO);
        transMapper.insert(trans);
    }

    @Override
    public void deleteTrans(TransDTO transDTO) {
        transDTO.checkDeleteParams();

        transMapper.deleteBatchIds(transDTO.getTransIds());
    }

    @Override
    public TransVO previewTransMd(TransDTO transDTO) {
        transDTO.checkPreviewParams();

        TransVO transVO = new TransVO();
        transVO.setContent(MarkdownLatexCleaner.cleanLatex(transDTO.getContent()));

        return transVO;
    }
}