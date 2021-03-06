package com.github.gustavovitor.integracoes_api.api.resource.auth_user;

import com.github.gustavovitor.integracoes_api.api.domain.auth_user.AuthUser;
import com.github.gustavovitor.integracoes_api.api.service.auth_user.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/public/user")
public class PublicAuthUserResource {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/register")
    public ResponseEntity<AuthUser> registerNewUser(@RequestBody @Valid AuthUser user) {
        return ResponseEntity.ok(authUserService.registerNewUser(user));
    }

    @PatchMapping("/confirm/{email}/{hash}")
    public ResponseEntity<AuthUser> confirmEmailByHash(@PathVariable String email, @PathVariable String hash) {
        return ResponseEntity.ok(authUserService.confirmEmail(email, hash));
    }

    @PatchMapping("/password/forget/{email}")
    public void forgetPassword(@PathVariable String email) {
        authUserService.forgetPassword(email);
    }

    @PatchMapping("/password/change/{email}/{hash}")
    public void changePassword(@PathVariable String email, @PathVariable String hash, @RequestBody String password) {
        authUserService.changePassword(email, hash, password);
    }
}
