package com.abc.system.service;

import com.abc.system.domain.dto.EmailDTO;
import com.abc.system.domain.vo.EmailVO;

public interface EmailService {

    EmailVO sendEmail(EmailDTO emailDTO);

    void invalidEmailCode(String emailUuid);
}
