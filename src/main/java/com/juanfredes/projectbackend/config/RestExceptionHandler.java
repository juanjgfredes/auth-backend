package com.juanfredes.projectbackend.config;

import com.juanfredes.projectbackend.dto.ErrorDto;
import com.juanfredes.projectbackend.exceptions.AppException;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler( value = AppException.class )
    public ResponseEntity<ErrorDto> personalException( AppException appEx ) {
        String menssage = appEx.getMessage();
        HttpStatus httpStatus = appEx.getHttpStatus();

        ErrorDto errorDto = new ErrorDto( menssage );

        return ResponseEntity
                .status( httpStatus )
                .body( errorDto );
    }

    @ExceptionHandler( value =  MethodArgumentNotValidException.class )
    public ResponseEntity<ErrorDto> validationsException( MethodArgumentNotValidException argException ) {
        ArrayList<ErrorDto> errores = new ArrayList<>();

        argException.getBindingResult().getAllErrors().forEach( err -> {
            String message = err.getDefaultMessage();

            errores.add( new ErrorDto( message ));
        });

        return ResponseEntity
                .status( HttpStatus.BAD_REQUEST )
                .body( errores.get( 0 ));
    }

    @ExceptionHandler( MongoWriteException.class )
    public ResponseEntity<ErrorDto> mongoException( MongoWriteException mongoEx ) {

        String message = switch ( mongoEx.getCode() ) {
            case 11000 -> "el email ingresado ya existe";
            default -> mongoEx.getMessage();
        };
        ErrorDto errorDto = new ErrorDto( message );

        return ResponseEntity
                .status( HttpStatus.BAD_REQUEST )
                .body( errorDto );
    }

}
