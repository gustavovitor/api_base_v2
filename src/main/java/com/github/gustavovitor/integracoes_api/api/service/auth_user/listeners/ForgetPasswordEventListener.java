package com.github.gustavovitor.integracoes_api.api.service.auth_user.listeners;

import com.github.gustavovitor.integracoes_api.api.service.auth_user.events.ForgetPasswordEvent;
import com.github.gustavovitor.integracoes_api.core.service.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ForgetPasswordEventListener implements ApplicationListener<ForgetPasswordEvent> {
    ForgetPasswordEvent event;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(ForgetPasswordEvent forgetPasswordEvent) {
        event = forgetPasswordEvent;
        notifyAuthUser();
    }

    private void notifyAuthUser() {
        emailService.send(EmailService.EmailMessage.builder()
                .subject("Alteração de Senha!")
                .templateName("{LAYOUT}")
                .recipient(event.getAuthUser().getEmail())
                .variable("FORGET_PASSWORD_HASH", event.getAuthUser().getForgetPasswordHash())
                .variable("EMAIL", event.getAuthUser().getEmail())
                .build());
    }
}
