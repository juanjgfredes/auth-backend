package com.juanfredes.projectbackend.config;

import com.juanfredes.projectbackend.dto.ErrorDto;
import com.juanfredes.projectbackend.exceptions.AppException;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler( value = AppException.class )
    public ResponseEntity<ErrorDto> personalException( AppException appEx ) {
        String menssage = appEx.getMessage();
        HttpStatus httpStatus = appEx.getHttpStatus();

        ErrorDto errorDto = new ErrorDto( "message", menssage );

        return ResponseEntity
                .status( httpStatus )
                .body( errorDto );
    }

    @ExceptionHandler( value =  MethodArgumentNotValidException.class )
    public ResponseEntity<ErrorDto> validationsException( MethodArgumentNotValidException argException ) {
        ErrorDto errorDto = new ErrorDto();

        argException.getBindingResult().getAllErrors().forEach( err -> {
            FieldError fieldError = (FieldError) err;
            String parametorName = fieldError.getField();
            String messageError = fieldError.getDefaultMessage();

            errorDto.add( parametorName, messageError);
        });

        return ResponseEntity
                .status( HttpStatus.BAD_REQUEST )
                .body(errorDto );
    }

    @ExceptionHandler( MongoWriteException.class )
    public ResponseEntity<ErrorDto> mongoException( MongoWriteException mongoEx ) {
        ErrorDto errorDto = new ErrorDto();

        String messageError = switch ( mongoEx.getCode() ) {
            case 11000 -> "el email ingresado ya existe";
            default -> mongoEx.getMessage();
        };
        errorDto.add("message", messageError);

        return ResponseEntity
                .status( HttpStatus.BAD_REQUEST )
                .body( errorDto );
    }

}
