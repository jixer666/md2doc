package com.abc.biz.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class AiCallDTO {

    private String model;

    private List<AiCallChoice> choices;

    @Data
    public static class AiCallChoice {

        private AiCallChoiceMessage message;

        private AiCallChoiceDelta delta;

    }

    @Data
    public static class AiCallChoiceMessage {

        private String message;

        private String role;

        private String reasoning_content;

    }

    @Data
    public static class AiCallChoiceDelta {

        private String role;

        private String content;

    }
}
