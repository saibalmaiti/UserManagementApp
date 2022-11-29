package com.cts.UserManagementApp.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("Message", message);
        responseMap.put("status", status);
        responseMap.put("Data", responseObj);
        return new ResponseEntity<Object>(responseMap, status);
    }
}
