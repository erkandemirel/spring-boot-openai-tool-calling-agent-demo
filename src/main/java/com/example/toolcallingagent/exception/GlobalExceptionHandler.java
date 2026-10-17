package com.example.toolcallingagent.exception;

import com.example.toolcallingagent.dto.ApiError;
import com.openai.errors.OpenAIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid request");

        return ResponseEntity.badRequest()
                .body(new ApiError("VALIDATION_ERROR", message));
    }

    @ExceptionHandler(OpenAIException.class)
    public ResponseEntity<ApiError> handleOpenAI(OpenAIException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ApiError("AI_SERVICE_UNAVAILABLE", "The AI service is temporarily unavailable"));
    }
}
