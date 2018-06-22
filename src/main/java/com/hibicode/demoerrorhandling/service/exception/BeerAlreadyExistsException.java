package com.hibicode.demoerrorhandling.service.exception;

import com.hibicode.demoerrorhandling.service.BusinessException;
import org.springframework.http.HttpStatus;

public class BeerAlreadyExistsException extends BusinessException {

    public BeerAlreadyExistsException() {
        super("beer.alreadyExists", HttpStatus.BAD_REQUEST);
    }

}
