package com.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class NotFoundExceptionUnitTest {

    @Test
    void CreateExceptionWithMessage() {
        String message = "Test message";
        NotFoundException exception = new NotFoundException(message);
        assertEquals(message, exception.getMessage());
    }
}
