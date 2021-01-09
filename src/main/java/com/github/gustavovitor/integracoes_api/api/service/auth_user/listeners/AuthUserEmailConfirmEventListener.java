package com.github.gustavovitor.integracoes_api.api.service.auth_user.listeners;

import com.github.gustavovitor.integracoes_api.api.service.auth_user.events.AuthUserCreatedEvent;
import com.github.gustavovitor.integracoes_api.core.service.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AuthUserEmailConfirmEventListener implements ApplicationListener<AuthUserCreatedEvent> {
    AuthUserCreatedEvent event;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(AuthUserCreatedEvent authUserCreatedEvent) {
        event = authUserCreatedEvent;
        notifyByEmail();
    }

    private void notifyByEmail() {
        emailService.send(EmailService.EmailMessage.builder()
                .subject("Confirme o seu cadastro!")
                .templateName("{LAYOUT}")
                .recipient(event.getCreatedUser().getEmail())
                .variable("CONFIRMATION_HASH", event.getCreatedUser().getEmailConfirmationHash())
                .variable("EMAIL", event.getCreatedUser().getEmail())
                .build());
    }
}
