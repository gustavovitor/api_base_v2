package com.github.gustavovitor.integracoes_api.api.service.auth_user.events;

import com.github.gustavovitor.integracoes_api.api.domain.auth_user.AuthUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

// Evento disparado quando o usuário solicita o "esqueci minha senha".
@Getter
public class ForgetPasswordEvent extends ApplicationEvent {
    AuthUser authUser;

    public ForgetPasswordEvent(Object source, AuthUser authUser) {
        super(source);
        this.authUser = authUser;
    }
}
