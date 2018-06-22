package com.hibicode.demoerrorhandling.error;

import com.hibicode.demoerrorhandling.error.ErrorResponse.ApiError;
import com.hibicode.demoerrorhandling.service.BusinessException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private static final String NO_MESSAGE_AVAILABLE = "No message available";
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private final MessageSource apiErrorMessageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception, Locale locale) {
        Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();
        List<ApiError> apiErrors = errors
                .map(ObjectError::getDefaultMessage)
                .map(this::createErrorCode)
                .map(code -> toApiError(code, locale))
                .collect(toList());

        return ResponseEntity.badRequest().body(ErrorResponse.ofErrors(HttpStatus.BAD_REQUEST, apiErrors));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessExceptions(BusinessException exception, Locale locale) {
        final ErrorCode errorCode = createErrorCode(exception.getCode(), exception.getStatus());
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.httpStatus(), toApiError(errorCode, locale));
        return ResponseEntity.status(errorCode.httpStatus()).body(errorResponse);
    }

    private ApiError toApiError(ErrorCode errorCode, Locale locale) {
        String message;
        try {
            message = apiErrorMessageSource.getMessage(errorCode.code(), new Object[]{}, locale);
        } catch (NoSuchMessageException e) {
            LOGGER.error("Couldn't find any message for {} code under {} locale", errorCode.code(), locale);
            message = NO_MESSAGE_AVAILABLE;
        }

        return new ApiError(errorCode.code(), message);
    }

    private ErrorCode createErrorCode(final String errorCode) {

        return new ErrorCode() {

            @Override
            public String code() {
                return errorCode;
            }

            @Override
            public HttpStatus httpStatus() {
                return HttpStatus.BAD_REQUEST;
            }
        };
    }

    private ErrorCode createErrorCode(final String errorCode, final HttpStatus status) {

        return new ErrorCode() {

            @Override
            public String code() {
                return errorCode;
            }

            @Override
            public HttpStatus httpStatus() {
                return status;
            }
        };
    }

}
