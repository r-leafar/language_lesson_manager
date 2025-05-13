package com.senai.api.infra;

import com.senai.api.domain.exception.AppointmentException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ApiResponse> handleNotFound(NoHandlerFoundException ex, HttpServletRequest httpServletRequest){

        ApiResponse apiResponse = new ApiResponse(404,"Resource not found");

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(apiResponse);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        var text = "This unique value is already in use by another record.";
        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(), text);
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(apiResponse);
    }

    @ExceptionHandler({AppointmentException.class})
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(AppointmentException ex){
        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(apiResponse);
    }

}
