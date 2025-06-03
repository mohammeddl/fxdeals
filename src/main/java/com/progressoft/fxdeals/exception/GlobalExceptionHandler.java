package com.progressoft.fxdeals.exception;

import com.progressoft.fxdeals.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        @ExceptionHandler(ValidationException.class)
        public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex,
                        HttpServletRequest request) {
                ErrorResponse body = new ErrorResponse(
                                OffsetDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                ex.getMessage(),
                                request.getRequestURI());
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(body);
        }

        @ExceptionHandler(InvalidCurrencyException.class)
        public ResponseEntity<ErrorResponse> handleInvalidCurrency(InvalidCurrencyException ex,
                        HttpServletRequest request) {
                ErrorResponse body = new ErrorResponse(
                                OffsetDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                ex.getMessage(),
                                request.getRequestURI());
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(body);
        }

        @ExceptionHandler(RequestAlreadyExistException.class)
        public ResponseEntity<ErrorResponse> handleDuplicate(RequestAlreadyExistException ex,
                        HttpServletRequest request) {
                ErrorResponse body = new ErrorResponse(
                                OffsetDateTime.now(),
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase(),
                                ex.getMessage(),
                                request.getRequestURI());
                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(body);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneric(Exception ex,
                        HttpServletRequest request) {
                log.error("Unhandled exception", ex);
                ErrorResponse body = new ErrorResponse(
                                OffsetDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                "An unexpected error occurred",
                                request.getRequestURI());
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(body);
        }
}
