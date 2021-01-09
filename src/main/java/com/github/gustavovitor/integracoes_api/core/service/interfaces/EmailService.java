package com.github.gustavovitor.integracoes_api.core.service.interfaces;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

public interface EmailService {
    void send(EmailMessage message);

    @Getter
    @Builder
    class EmailMessage {

        @Singular
        private Set<String> recipients;
        private String subject;
        private String templateName;

        @Singular
        private Map<String, Object> variables;
    }
}
