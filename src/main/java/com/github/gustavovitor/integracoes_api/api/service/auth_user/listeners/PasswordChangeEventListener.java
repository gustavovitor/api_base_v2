package com.github.gustavovitor.integracoes_api.api.service.auth_user.listeners;

import com.github.gustavovitor.integracoes_api.api.service.auth_user.events.PasswordChangeEvent;
import com.github.gustavovitor.integracoes_api.core.service.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PasswordChangeEventListener implements ApplicationListener<PasswordChangeEvent> {
    PasswordChangeEvent event;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(PasswordChangeEvent passwordChangeEvent) {
        event = passwordChangeEvent;
        notifyAuthUser();
    }

    private void notifyAuthUser() {
        emailService.send(EmailService.EmailMessage.builder()
                .subject("A sua senha foi alterada!")
                .templateName("{LAYOUT}")
                .recipient(event.getAuthUser().getEmail())
                .build());
    }

}
