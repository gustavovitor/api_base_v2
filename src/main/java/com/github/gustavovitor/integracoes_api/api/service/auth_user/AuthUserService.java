package com.github.gustavovitor.integracoes_api.api.service.auth_user;

import com.github.gustavovitor.integracoes_api.api.domain.auth_user.Permission;
import com.github.gustavovitor.integracoes_api.api.repository.permission.PermissionRepository;
import com.github.gustavovitor.integracoes_api.api.repository.auth_user.AuthUserRepository;
import com.github.gustavovitor.integracoes_api.api.domain.auth_user.AuthUser;
import com.github.gustavovitor.integracoes_api.api.repository.auth_user.specification.AuthUserSpecification;
import com.github.gustavovitor.maker.service.ServiceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthUserService extends ServiceMaker<AuthUserRepository, AuthUser, Long, AuthUser, AuthUserSpecification> {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public AuthUser insert(AuthUser user) {
        user.setId(null);
        user.setPass(encoder.encode(user.getPass()));
        setDefaultUserPermissions(user);
        return super.insert(user);
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
