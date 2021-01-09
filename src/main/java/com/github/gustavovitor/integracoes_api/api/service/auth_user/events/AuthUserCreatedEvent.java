package com.github.gustavovitor.integracoes_api.api.service.auth_user.events;

import com.github.gustavovitor.integracoes_api.api.domain.auth_user.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

// Evento disparado sempre que um novo usuário é criado no sistema.
@Getter
public class AuthUserCreatedEvent extends ApplicationEvent {
    AuthUser createdUser;

    public AuthUserCreatedEvent(Object source, AuthUser createdUser) {
        super(source);
        this.createdUser = createdUser;
    }
}
