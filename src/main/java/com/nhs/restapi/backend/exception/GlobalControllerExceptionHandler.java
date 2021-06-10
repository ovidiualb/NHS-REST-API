package com.nhs.restapi.backend.exception;

import com.nhs.restapi.backend.exception.business.GlobalAlreadyExistsException;
import com.nhs.restapi.backend.exception.business.GlobalDatabaseException;
import com.nhs.restapi.backend.exception.business.GlobalNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalNotFoundException.class)
    public ResponseEntity<Object> handleGlobalNotFoundException(GlobalNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GlobalAlreadyExistsException.class)
    public ResponseEntity<Object> handleGlobalAlreadyExistsException(GlobalAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GlobalDatabaseException.class)
    public ResponseEntity<Object> handleGlobalDatabaseException(GlobalDatabaseException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> errors.put("VALIDATION ERROR IN FIELD \'" + fieldError.getField() + "\'", fieldError.getDefaultMessage()));
        return new ResponseEntity<>(errors, status);
    }
}
