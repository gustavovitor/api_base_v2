package com.github.gustavovitor.integracoes_api.api.repository.auth_user.specification;

import com.github.gustavovitor.integracoes_api.api.domain.auth_user.AuthUser;
import com.github.gustavovitor.maker.repository.SpecificationBase;

import javax.management.ReflectionException;

public class AuthUserSpecification extends SpecificationBase<AuthUser> {
    public AuthUserSpecification(AuthUser object) throws ReflectionException {
        super(object);
    }
}
