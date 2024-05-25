package com.crypto.trade.api.exception;

import com.crypto.trade.api.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{
    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<ApiResponse> handle(MyCustomException ex)
    {
        String message=ex.getMessage().substring(0,ex.getMessage().indexOf("#"));
       String Exception=ex.getMessage().substring(ex.getMessage().lastIndexOf("#"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(message));
    }
}
