package com.nerd.need_server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomException extends Exception {

    private final HttpStatus code;
    private final String message;

}
