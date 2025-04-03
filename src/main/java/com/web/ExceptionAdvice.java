package com.web;


import com.exception.NotFoundException;
import com.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionAdvice {

@ResponseStatus(HttpStatus.NOT_FOUND)
@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(){
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Resource not found!");
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
}

@ResponseStatus(HttpStatus.BAD_GATEWAY)
@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> genericHandler(){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_GATEWAY.value(),"Oops! Something went wrong!");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_GATEWAY);
    }

}
