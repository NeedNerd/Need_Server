package com.nerd.need_server.exception;

import com.nerd.need_server.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<BaseResponse<Void>> handleAllException(CustomException exception) {
        BaseResponse<Void> returnValue = new BaseResponse<>(exception.getMessage(), exception.getCode().value(), null);

        return new ResponseEntity<>(returnValue, exception.getCode());
    }
}
