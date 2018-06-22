package com.hibicode.demoerrorhandling.resource;

import com.hibicode.demoerrorhandling.model.Beer;
import com.hibicode.demoerrorhandling.service.exception.BeerAlreadyExistsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/beers")
public class BeersResource {

    @PostMapping
    public void createBeer(@Valid @RequestBody Beer beer) {
        // Usually we will call the service layer, but for this demo I'll throw an exception
        throw new BeerAlreadyExistsException();

    }

}
