package com.github.gustavovitor.integracoes_api.api.repository.auth_user;

import com.github.gustavovitor.integracoes_api.api.domain.auth_user.AuthUser;
import com.github.gustavovitor.maker.repository.RepositoryMaker;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends RepositoryMaker<AuthUser, Long> {
    Optional<AuthUser> findByUser(String email);
}