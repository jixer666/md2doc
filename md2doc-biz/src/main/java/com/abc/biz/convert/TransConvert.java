package com.abc.biz.convert;

import cn.hutool.core.bean.BeanUtil;
import com.abc.biz.domain.dto.AiMessageDTO;
import com.abc.biz.domain.vo.TransVO;
import com.abc.common.util.IdUtils;
import com.abc.common.util.SecurityUtils;
import com.abc.biz.domain.dto.TransDTO;
import com.abc.biz.domain.entity.Trans;

import java.util.ArrayList;
import java.util.List;

/**
 * MD转换转换器
 *
 * @author LiJunXi
 * @date 2025-10-20
 */
public class TransConvert {
    public static Trans buildDefaultTransByTransDTO(TransDTO transDTO) {
        Trans trans = BeanUtil.copyProperties(transDTO, Trans.class);
        trans.setTransId(IdUtils.getId());
        trans.setUserId(SecurityUtils.getUserId());
        trans.setCommonParams();

        return trans;
    }

    public static List<AiMessageDTO> buildAiMessageDTOByContent(String content) {
        List<AiMessageDTO> messageList = new ArrayList<>();
        messageList.add(new AiMessageDTO(content));
        return messageList;
    }

    public static TransVO buildTransVOByTransContent(String transContent) {
        TransVO transVO = new TransVO();
        transVO.setTransContent(transContent);

        return transVO;
    }

}
