package com.abc.biz.service;

import com.abc.biz.domain.vo.TransVO;
import com.abc.common.domain.vo.PageResult;
import com.abc.biz.domain.dto.TransDTO;
import com.abc.biz.domain.entity.Trans;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * MD转换接口
 *
 * @author LiJunXi
 * @date 2025-10-20
 */
public interface TransService extends IService<Trans> {

    PageResult getTransPageWithUiParam(TransDTO transDTO);

    void updateTrans(TransDTO transDTO);

    void saveTrans(TransDTO transDTO);

    void deleteTrans(TransDTO transDTO);

    TransVO previewTransMd(TransDTO transDTO);
}
