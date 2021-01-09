package com.github.gustavovitor.integracoes_api.core.handlers.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorHandler {

    private String message;
    private String error;
    private Integer code;
    private Object object;

    public ErrorHandler(String message, String error) {
        this.message = message;
        this.error = error;
        this.code = ErrorCodes.DEFAULT_ERROR; // Default error code.
        this.object = null;
    }

    public ErrorHandler(String message, String error, Integer code) {
        this.message = message;
        this.error = error;
        this.code = code;
        this.object = null;
    }

    public ErrorHandler(String message, String error, Integer code, Object o) {
        this.message = message;
        this.error = error;
        this.code = code;
        this.object = o;
    }
}