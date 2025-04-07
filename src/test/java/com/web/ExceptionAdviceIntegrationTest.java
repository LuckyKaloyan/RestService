package com.web;

import com.web.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



public class ExceptionAdviceIntegrationTest {

    private final ExceptionAdvice exceptionAdvice = new ExceptionAdvice();

    @Test
    void handleNotFoundReturnNotFoundResponse() {
        ResponseEntity<ErrorResponse> response = exceptionAdvice.handleNotFound();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals("Resource not found!", response.getBody().getMessage());
    }

    @Test
    void genericHandlerReturnBadGatewayResponse() {
        ResponseEntity<ErrorResponse> response = exceptionAdvice.genericHandler();
        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_GATEWAY.value(), response.getBody().getStatus());
        assertEquals("Oops! Something went wrong!", response.getBody().getMessage());
    }
}
