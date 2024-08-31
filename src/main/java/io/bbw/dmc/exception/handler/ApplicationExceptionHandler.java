package io.bbw.dmc.exception.handler;

import io.formulate.web.common.error.AppError;
import io.formulate.web.common.exception.AppException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedList;
import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<AppError> handleApplicationException(AppException exception) {
        return new ResponseEntity<>(new AppError(exception.getMessage()), exception.getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AppError> handleRuntimeException(RuntimeException exception) {
        return new ResponseEntity<>(new AppError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorMessages = new LinkedList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errorMessages.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(new AppError(errorMessages), HttpStatus.BAD_REQUEST);
    }
}
