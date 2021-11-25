package it.com.demo.exception;

import it.com.demo.model.ErrorResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class QuotesExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = QuoteException.class)
    public ResponseEntity<ErrorResponse> handleException(QuoteException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
