package com.abc.biz.service.impl;

import com.abc.biz.domain.dto.AiMessageDTO;
import com.abc.biz.service.AiService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {


    private final WebClient webClient;

    private final String apiKye = "NFespmefXzGdviJyBgnU:npGoYFRcVypSvEYqRKwG";
//    private final String apiKye = "sk-6ac0acf0a8a647e69275033402185bc3";


    public AiServiceImpl() {
        this.webClient = WebClient.create("https://spark-api-open.xf-yun.com/v2");
    }


    @Override
    public WebClient.ResponseSpec callAiServer(List<AiMessageDTO> messageList, Boolean stream) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "x1");

        requestBody.put("messages", messageList);
        requestBody.put("stream", stream);

        return webClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .header("Authorization", "Bearer " + apiKye)
                .bodyValue(requestBody)
                .retrieve();

    }
}
