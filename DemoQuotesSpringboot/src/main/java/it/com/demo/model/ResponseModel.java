package it.com.demo.model;

import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

public class ResponseModel {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);

        return new ResponseEntity<>(map, status);
    }
}
