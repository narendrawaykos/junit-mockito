package com.company.junitmockito.util;

import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@NoArgsConstructor
public class ErrorResult {
    private final List<ControllerExceptionHandler.FieldValidationError> fieldErrors = new ArrayList<>();


    public ErrorResult(String field, String message) {
        this.fieldErrors.add(new ControllerExceptionHandler.FieldValidationError(field, message));
    }
}

