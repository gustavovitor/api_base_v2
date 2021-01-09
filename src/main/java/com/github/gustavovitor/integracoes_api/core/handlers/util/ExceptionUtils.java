package com.github.gustavovitor.integracoes_api.core.handlers.util;

import com.github.gustavovitor.integracoes_api.core.handlers.error.ErrorHandler;
import com.github.gustavovitor.util.MessageUtil;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ExceptionUtils {

    public static List<ErrorHandler> createErrorList(BindingResult bindingResult) {
        List<ErrorHandler> errs = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errs.add(new ErrorHandler(MessageUtil.getMessage(fieldError), fieldError.toString()));
        }
        return errs;
    }

}
