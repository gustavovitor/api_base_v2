package com.github.gustavovitor.integracoes_api.api.service.auth_user;

import com.github.gustavovitor.integracoes_api.api.domain.auth_user.Permission;
import com.github.gustavovitor.integracoes_api.api.repository.permission.PermissionRepository;
import com.github.gustavovitor.integracoes_api.api.repository.auth_user.AuthUserRepository;
import com.github.gustavovitor.integracoes_api.api.domain.auth_user.AuthUser;
import com.github.gustavovitor.integracoes_api.api.repository.auth_user.specification.AuthUserSpecification;
import com.github.gustavovitor.integracoes_api.api.service.auth_user.events.AuthUserCreatedEvent;
import com.github.gustavovitor.integracoes_api.api.service.auth_user.events.ForgetPasswordEvent;
import com.github.gustavovitor.integracoes_api.api.service.auth_user.events.PasswordChangeEvent;
import com.github.gustavovitor.integracoes_api.api.service.auth_user.exceptions.AuthUserAlreadyConfirmedException;
import com.github.gustavovitor.integracoes_api.api.service.auth_user.exceptions.InvalidAuthUserConfirmationHashException;
import com.github.gustavovitor.integracoes_api.api.service.auth_user.exceptions.InvalidForgetPasswordHashException;
import com.github.gustavovitor.integracoes_api.api.service.auth_user.exceptions.UserAlreadyRegisteredException;
import com.github.gustavovitor.maker.service.ServiceMaker;
import com.github.gustavovitor.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class AuthUserService extends ServiceMaker<AuthUserRepository, AuthUser, Long, AuthUser, AuthUserSpecification> {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public AuthUser insert(AuthUser user) {
        user.setId(null);
        user.setPass(encoder.encode(user.getPass()));
        return super.insert(user);
    }

    public AuthUser registerNewUser(AuthUser userToRegister) {
        if (getRepository().findByEmail(userToRegister.getEmail()).isPresent()) {
            throw new UserAlreadyRegisteredException();
        }

        setDefaultUserPermissions(userToRegister);
        userToRegister.setEmailConfirmationHash(UUID.randomUUID().toString());
        userToRegister.setEmailConfirmed(false);
        AuthUser savedAuthUser = this.insert(userToRegister);

        applicationEventPublisher.publishEvent(new AuthUserCreatedEvent(this, savedAuthUser));
        return savedAuthUser;
    }

    public void forgetPassword(String email) {
        AuthUser authUser = findByEmail(email);
        authUser.setForgetPasswordHash(UUID.randomUUID().toString());
        authUser = getRepository().save(authUser);
        applicationEventPublisher.publishEvent(new ForgetPasswordEvent(this, authUser));
    }

    public void changePassword(String email, String hash, String password) {
        AuthUser authUser = findByEmail(email);
        if (isNull(authUser.getForgetPasswordHash()) || !authUser.getForgetPasswordHash().equals(hash)) {
            throw new InvalidForgetPasswordHashException();
        }
        authUser.setForgetPasswordHash(null);
        authUser.setPass(encoder.encode(password));
        getRepository().save(authUser);
        applicationEventPublisher.publishEvent(new PasswordChangeEvent(this, authUser));
    }

    public AuthUser findByEmail(String email) {
        return getRepository().findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(MessageUtil.getMessage("auth.user.not.found.by.email")));
    }

    public AuthUser confirmEmail(String email, String hash) {
        AuthUser authUser = findByEmail(email);

        if (!authUser.getEmailConfirmationHash().equals(hash)) {
            throw new InvalidAuthUserConfirmationHashException();
        }

        if (authUser.getEmailConfirmed()) {
            throw new AuthUserAlreadyConfirmedException();
        }

        authUser.setEmailConfirmed(true);
        return getRepository().save(authUser);
    }

    private void setDefaultUserPermissions(AuthUser user) {
        List<Permission> permissions = permissionRepository.findAll();

        List<Permission> userDefaultPermissions = new ArrayList<>();
        userDefaultPermissions
                .add(permissions
                        .stream()
                        .filter(permission -> permission
                                .getDescription()
                                .equals("READ_USERS"))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Permissão não encontrada!"))
                );

        user.setPermissions(userDefaultPermissions);
    }

}
