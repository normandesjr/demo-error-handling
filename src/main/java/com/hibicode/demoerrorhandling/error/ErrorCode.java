package com.hibicode.demoerrorhandling.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String code();

    HttpStatus httpStatus();

}