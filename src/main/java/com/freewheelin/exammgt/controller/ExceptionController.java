package com.freewheelin.exammgt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    // 500
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Map<String, Object>> handleAll(final Exception ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "500");
        map.put("msg" , ex.getMessage());

        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
