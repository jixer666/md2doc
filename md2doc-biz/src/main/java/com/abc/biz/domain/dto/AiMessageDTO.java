package com.abc.biz.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiMessageDTO {

    private String role = "user";

    private String content;

    public AiMessageDTO(String genBackendPrompt) {
        this.content = genBackendPrompt;
    }
}
