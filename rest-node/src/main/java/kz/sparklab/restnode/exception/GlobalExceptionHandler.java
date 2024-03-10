package kz.sparklab.restnode.exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllExceptions(Exception ex) {
        return new ErrorResponse("Internal Server Error", ex.getMessage());
    }
    @ExceptionHandler(BoxNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(BoxNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Not Found", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
