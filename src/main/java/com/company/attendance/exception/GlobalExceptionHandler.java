package com.company.attendance.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔴 Custom Exception (User + Attendance errors)
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustom(CustomException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 🔴 Handle Illegal Argument (optional but useful)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 🔴 Handle Null Pointer (common runtime issue)
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNull(NullPointerException ex) {
        return new ResponseEntity<>("Null value error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 🔴 Generic Exception (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}