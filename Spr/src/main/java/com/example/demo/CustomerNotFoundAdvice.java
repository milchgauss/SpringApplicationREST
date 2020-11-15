package com.example.demo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerNotFoundAdvice {

    @ResponseBody

    //Signals that the advice is rendered straight to the response body
    @ExceptionHandler(CustomerNotFoundException.class)
    //Configures the advice to only render if it is in a particular exception
    @ResponseStatus(HttpStatus.NOT_FOUND)
    //Issue an HTTP 404 Response
    String employeeNotFoundHandler(CustomerNotFoundException ex) {
        return ex.getMessage();
    }
}



