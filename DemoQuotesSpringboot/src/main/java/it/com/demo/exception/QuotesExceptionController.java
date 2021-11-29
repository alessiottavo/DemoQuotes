package it.com.demo.exception;

import it.com.demo.Controller.AuthorController;
import it.com.demo.model.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class QuotesExceptionController extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(QuotesExceptionController.class);

    @ExceptionHandler(value = QuoteException.class)
    public ResponseEntity<ErrorResponse> handleException(QuoteException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage());
        logger.error("Error thrown");
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
