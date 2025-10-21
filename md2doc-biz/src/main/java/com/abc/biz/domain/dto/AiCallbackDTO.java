package com.abc.biz.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class AiCallbackDTO {

    private String model;
    private List<Choice> choices;

    @Data
    public static class Choice {
        private AiMessageDTO delta;
        private String finish_reason;
        private AiMessageDTO message;
    }

}
