package com.act.emp.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    @Test
    public void handleEmployeeNotFoundException_ReturnsNotFound() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        EmployeeNotFoundException ex = new EmployeeNotFoundException("Employee not found");

        ResponseEntity<String> response = handler.handleEmployeeNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Employee not found", response.getBody());
    }

    @Test
    public void handleGenericException_ReturnsInternalServerError() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new Exception("Some unexpected error");

        ResponseEntity<String> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred: Some unexpected error", response.getBody());
    }
}
