package com.abc.biz.service;

import com.abc.biz.domain.dto.AiMessageDTO;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public interface AiService {

    WebClient.ResponseSpec callAiServer(List<AiMessageDTO> messageList, Boolean stream);

}
