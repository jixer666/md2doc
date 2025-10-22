package com.abc.system.strategy.email;

import com.abc.system.domain.dto.EmailDTO;
import com.abc.system.domain.vo.EmailVO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

public interface EmailStrategy {

    EmailVO sendEmail(EmailDTO emailDTO);

    String getTitle(Map<String, String> params);

    String getContent(Map<String, String> params);

    default void doSend(JavaMailSender mailSender, EmailDTO emailDTO) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(emailDTO.getFrom());
        helper.setTo(emailDTO.getTo());
        helper.setSubject(getTitle(emailDTO.getDetailsMap()));
        helper.setText(getContent(emailDTO.getDetailsMap()), true);
        mailSender.send(message);
    }

}
