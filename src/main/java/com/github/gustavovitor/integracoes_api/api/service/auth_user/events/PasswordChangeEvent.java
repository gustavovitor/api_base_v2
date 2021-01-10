package com.github.gustavovitor.integracoes_api.api.service.auth_user.events;

import com.github.gustavovitor.integracoes_api.api.domain.auth_user.AuthUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

// Evento chamado quando o usuário altera a própria senha.
@Getter
public class PasswordChangeEvent extends ApplicationEvent {
    AuthUser authUser;

    public PasswordChangeEvent(Object source, AuthUser authUser) {
        super(source);
        this.authUser = authUser;
    }
}
