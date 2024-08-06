package com.food.exception;

import com.food.dto.response.ExceptionResponse;
import com.food.exception.common.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiExceptionParserTest {

    @Test
    void testParseException() {
        ApiException mockedException = mock(ApiException.class);
        when(mockedException.getMessage()).thenReturn("Test Exception");
        when(mockedException.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        when(mockedException.getStatusCode()).thenReturn(500);

        ExceptionResponse result = ApiExceptionParser.parseException(mockedException);

        assertEquals(LocalDateTime.now().getYear(), result.getDateTime().getYear());
        assertEquals("Test Exception", result.getMessage());
        assertEquals(500, result.getStatusCode());
    }

}