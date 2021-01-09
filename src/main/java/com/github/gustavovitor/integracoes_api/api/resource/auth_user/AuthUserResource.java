package com.github.gustavovitor.integracoes_api.api.resource.auth_user;

import com.github.gustavovitor.integracoes_api.api.domain.auth_user.AuthUser;
import com.github.gustavovitor.integracoes_api.api.service.auth_user.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class AuthUserResource {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/register")
    public ResponseEntity<AuthUser> registerUser(@RequestBody @Valid  AuthUser user) {
        return ResponseEntity.ok(authUserService.insert(user));
    }
}
