package com.github.gustavovitor.integracoes_api.core.service.impl;

import com.github.gustavovitor.integracoes_api.core.service.interfaces.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Profile("development")
public class MockEmailService implements EmailService {

    @Override
    public void send(EmailMessage message) {
       log.info("[MOCK] Enviando e-mail fake!");
       log.info("[MOCK] Subject: ".concat(message.getSubject()));
       log.info("[MOCK] Template: ".concat(message.getTemplateName()));
       log.info("[MOCK] Recipients: ".concat(message.getRecipients().toString()));
       log.info("[MOCK] Variables: ".concat(message.getVariables().toString()));
       log.info("[MOCK] End!");
    }
}
