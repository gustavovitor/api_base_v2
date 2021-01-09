package com.github.gustavovitor.integracoes_api.core.handlers;

import com.github.gustavovitor.integracoes_api.api.service.auth_user.exceptions.AuthUserAlreadyConfirmedException;
import com.github.gustavovitor.integracoes_api.api.service.auth_user.exceptions.InvalidAuthUserConfirmationHashException;
import com.github.gustavovitor.integracoes_api.api.service.auth_user.exceptions.UserAlreadyRegisteredException;
import com.github.gustavovitor.integracoes_api.core.handlers.error.APIError;
import com.github.gustavovitor.integracoes_api.core.handlers.error.ErrorHandler;
import com.github.gustavovitor.util.MessageUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class AuthUserHandler extends APIExceptionHandler {

    @ExceptionHandler({InvalidAuthUserConfirmationHashException.class})
    public ResponseEntity<Object> invalidAuthUserConfirmationHashException(InvalidAuthUserConfirmationHashException ex, WebRequest request) {
        String mensagem = MessageUtil.getMessage("invalid.auth.user.confirmation.hash.exception");
        List<ErrorHandler> erros = Collections.singletonList(new ErrorHandler(mensagem, ex.toString()));
        return handleExceptionInternal(ex, new APIError(erros.size(), erros), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({AuthUserAlreadyConfirmedException.class})
    public ResponseEntity<Object> authUserAlreadyConfirmed(AuthUserAlreadyConfirmedException ex, WebRequest request) {
        String mensagem = MessageUtil.getMessage("auth.user.already.confirmed.exception");
        List<ErrorHandler> erros = Collections.singletonList(new ErrorHandler(mensagem, ex.toString()));
        return handleExceptionInternal(ex, new APIError(erros.size(), erros), new HttpHeaders(), HttpStatus.ALREADY_REPORTED, request);
    }

    @ExceptionHandler({UserAlreadyRegisteredException.class})
    public ResponseEntity<Object> userAlreadyRegisteredException(UserAlreadyRegisteredException ex, WebRequest request) {
        String mensagem = MessageUtil.getMessage("user.already.registered.exception");
        List<ErrorHandler> erros = Collections.singletonList(new ErrorHandler(mensagem, ex.toString()));
        return handleExceptionInternal(ex, new APIError(erros.size(), erros), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}
