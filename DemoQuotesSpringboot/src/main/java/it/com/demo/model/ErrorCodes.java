package it.com.demo.model;

import org.springframework.http.HttpStatus;

public enum ErrorCodes {
    QUOTE_NOT_FOUND(101, HttpStatus.NOT_FOUND, "Elemento non trovato"),
    QUOTE_ALREADY_PRESENT(102, HttpStatus.CONFLICT, "Elemento già presente"),
    QUOTE_BADLY_WRITTEN(103, HttpStatus.BAD_REQUEST, "Elemento non fornito correttamente"),
    AUTHOR_NOT_FOUND(104, HttpStatus.NOT_FOUND, "Autore non trovato");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCodes(int code, HttpStatus status, String message) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
