package com.abc.system.convert;

import com.abc.system.domain.dto.CaptchaDTO;
import com.abc.system.domain.vo.CaptchaVO;

public class CaptchaConvert {
    public static CaptchaVO convertCaptchaVOByCaptchaDTO(CaptchaDTO captchaDTO) {
        return CaptchaVO.builder()
                .img(captchaDTO.getImg())
                .uuid(captchaDTO.getUuid())
                .build();
    }
}
