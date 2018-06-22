package com.hibicode.demoerrorhandling.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String ERROR_CODE_FOR_UNKNOWN_ERROR = "unknown";

    String code();

    HttpStatus httpStatus();

    enum UnknownErrorCode implements ErrorCode {
        INSTANCE;

        @Override
        public String code() {
            return ERROR_CODE_FOR_UNKNOWN_ERROR;
        }

        @Override
        public HttpStatus httpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}