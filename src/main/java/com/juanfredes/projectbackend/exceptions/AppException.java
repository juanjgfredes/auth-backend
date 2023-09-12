package com.juanfredes.projectbackend.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppException extends RuntimeException {
    private HttpStatus httpStatus;

    public AppException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

}
