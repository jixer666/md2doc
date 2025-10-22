package com.abc.system.convert;

import com.abc.system.constant.EmailConstants;
import com.abc.system.domain.dto.EmailDTO;
import com.abc.system.domain.vo.EmailVO;

public class EmailConvert {

    public static EmailVO buildEmailVoByEmailDTO(EmailDTO emailDTO) {
        EmailVO emailVO = new EmailVO();
        emailVO.setEmailUuid(emailDTO.getEmailUuid());
        emailVO.setEmail(emailDTO.getDetailsMap().get(EmailConstants.EMAIL_CODE));

        return emailVO;
    }
}
