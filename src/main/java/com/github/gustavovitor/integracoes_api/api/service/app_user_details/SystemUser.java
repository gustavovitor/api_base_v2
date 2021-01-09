package com.github.gustavovitor.integracoes_api.api.service.app_user_details;

import com.github.gustavovitor.integracoes_api.api.domain.auth_user.AuthUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SystemUser extends User {
    private static final Long serialVersionUID = 1L;

    @Getter
    private AuthUser authUser;

    /** {@link SystemUser} é usado pelo Spring Security, já que extende de User, o serviço que o usará geralmente
     * extende ou é um {@link com.github.gustavovitor.integracoes_api.api.service.app_user_details.AppUserDetailsService}.
     * */
    public SystemUser(AuthUser user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUser(), user.getPass(), authorities);
        this.authUser = user;
    }
}
