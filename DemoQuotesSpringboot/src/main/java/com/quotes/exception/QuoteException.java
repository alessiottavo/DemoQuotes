package com.quotes.exception;

import com.quotes.model.ErrorCodes;
import org.springframework.http.HttpStatus;

public class QuoteException extends Exception {
    private final int code;
    private final String message;
    private final HttpStatus status;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public QuoteException(ErrorCodes code) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.status = code.getStatus();
    }

    public HttpStatus getStatus() {
        return status;
    }
}
