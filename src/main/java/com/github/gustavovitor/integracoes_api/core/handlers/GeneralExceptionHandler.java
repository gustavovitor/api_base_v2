package com.github.gustavovitor.integracoes_api.core.handlers;

import com.github.gustavovitor.exceptions.ValidationException;
import com.github.gustavovitor.integracoes_api.core.handlers.error.APIError;
import com.github.gustavovitor.integracoes_api.core.handlers.error.ErrorCodes;
import com.github.gustavovitor.integracoes_api.core.handlers.error.ErrorHandler;
import com.github.gustavovitor.util.MessageUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

import static com.github.gustavovitor.integracoes_api.core.handlers.util.ExceptionUtils.createErrorList;

@ControllerAdvice
public class GeneralExceptionHandler extends APIExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String mensagem = MessageUtil.getMessage("invalid.message");
        List<ErrorHandler> erros = Collections.singletonList(new ErrorHandler(mensagem, ex.getCause() != null ? ex.getCause().toString() : ex.toString()));
        return handleExceptionInternal(ex, new APIError(erros.size(), erros), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorHandler> erros = createErrorList(ex.getBindingResult());
        return handleExceptionInternal(ex, new APIError(erros.size(), erros), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorHandler> erros = createErrorList(ex.getBindingResult());
        return handleExceptionInternal(ex, new APIError(erros.size(), erros), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> validationException(ValidationException ex, WebRequest request) {
        List<ErrorHandler> erros = createErrorList(ex.getBindingResult());
        return handleExceptionInternal(ex, new APIError(erros.size(), erros), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> genericError(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        String mensagem = MessageUtil.getMessage("generic.error");
        List<ErrorHandler> erros = Collections.singletonList(new ErrorHandler(mensagem, ex.toString(), ErrorCodes.GENERIC, ex.getMessage()));
        return handleExceptionInternal(ex, new APIError(erros.size(), erros), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ResponseEntity<Object> internalAuthenticationException(RuntimeException ex, WebRequest request) {
        String mensagem = MessageUtil.getMessage("invalid.user.or.password");
        List<ErrorHandler> erros = Collections.singletonList(new ErrorHandler(mensagem, ex.toString()));
        return handleExceptionInternal(ex, new APIError(erros.size(), erros), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> entityNotFoundException(RuntimeException ex, WebRequest request) {
        String mensagem = ex.getMessage();
        List<ErrorHandler> erros = Collections.singletonList(new ErrorHandler(mensagem, ex.toString()));
        return handleExceptionInternal(ex, new APIError(erros.size(), erros), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
