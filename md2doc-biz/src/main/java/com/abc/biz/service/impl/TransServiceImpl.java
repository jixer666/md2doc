package com.abc.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.abc.biz.constant.BizConstants;
import com.abc.biz.domain.dto.AiCallbackDTO;
import com.abc.biz.domain.dto.AiMessageDTO;
import com.abc.biz.domain.enums.PointsRuleTypeEnum;
import com.abc.biz.factory.TransAsyncTaskFactory;
import com.abc.biz.service.AiService;
import com.abc.biz.service.component.PointsServiceHelper;
import com.abc.biz.util.MarkdownLatexCleaner;
import com.abc.biz.util.PandocUtil;
import com.abc.common.core.async.AsyncManager;
import com.abc.common.core.service.BaseServiceImpl;
import com.abc.common.domain.vo.PageResult;
import com.abc.common.util.AssertUtils;
import com.abc.biz.convert.TransConvert;
import com.abc.biz.domain.dto.TransDTO;
import com.abc.biz.domain.entity.Trans;
import com.abc.biz.domain.vo.TransVO;
import com.abc.biz.mapper.TransMapper;
import com.abc.biz.service.TransService;
import com.abc.common.util.FileUtils;
import com.abc.common.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * MD转换业务处理
 *
 * @author LiJunXi
 * @date 2025-10-20
 */
@Slf4j
@Service
public class TransServiceImpl extends BaseServiceImpl<TransMapper, Trans> implements TransService {

    @Autowired
    private TransMapper transMapper;

    @Autowired
    private AiService aiService;

    @Autowired
    private PointsServiceHelper pointsServiceHelper;

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

        String content = MarkdownLatexCleaner.cleanLatex(transDTO.getPreContent());

        if (!SecurityUtils.isAnonymousUser()) {
            transDTO.setUserId(SecurityUtils.getUserId());
            AsyncManager.me().execute(TransAsyncTaskFactory.recordTransTask(transDTO, content));
        }

        return TransConvert.buildTransVOByTransContent(content);
    }

    @Override
    @Transactional
    public TransVO previewTransMdByAi(TransDTO transDTO) {
        transDTO.checkPreviewParams();

        return pointsServiceHelper.executeWithPoints(SecurityUtils.getUserId(), PointsRuleTypeEnum.EXPORT, () -> {
            List<AiMessageDTO> messageList = TransConvert.buildAiMessageDTOByContent(transDTO.getPreContent());
            String result = getAiCallMessageResult(messageList);
            transDTO.setUserId(SecurityUtils.getUserId());
            AsyncManager.me().execute(TransAsyncTaskFactory.recordTransTask(transDTO, result));
            return TransConvert.buildTransVOByTransContent(result);
        });
    }

    private String getAiCallMessageResult(List<AiMessageDTO> messageList) {
        WebClient.ResponseSpec responseSpec = aiService.callAiServer(messageList, true);
        return responseSpec.bodyToMono(AiCallbackDTO.class)
                .filter(response -> response.getChoices() != null && !response.getChoices().isEmpty())
                .map(response -> {
                    AiCallbackDTO.Choice choice = response.getChoices().get(0);
                    if (choice == null || choice.getMessage() == null) {
                        return "";
                    }
                    String content = choice.getMessage().getContent();
                    return content != null ? content : "";
                }).block();
    }

    @Override
    public ResponseEntity<byte[]> exportTransMd(TransDTO transDTO) {
        transDTO.checkExportParams();

        return pointsServiceHelper.executeWithPoints(SecurityUtils.getUserId(), PointsRuleTypeEnum.EXPORT, () -> {
            byte[] data = PandocUtil.transMdToWord(transDTO.getPreContent());
            return download(data, FileUtils.getFilenameByDate(BizConstants.EXPORT_STR, BizConstants.DOCX_EXTENSION));
        });
    }
}