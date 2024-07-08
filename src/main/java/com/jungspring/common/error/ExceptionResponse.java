package com.jungspring.common.error;


import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ExceptionResponse {

    private String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public static ExceptionResponse from(Exception exception) {
        return new ExceptionResponse(exception.getMessage());
    }


}
