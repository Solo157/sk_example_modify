package com.java.update.api;

import com.java.update.service.SKExampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class SKExampleApi {

    private final SKExampleService service;

    public SKExampleApi(SKExampleService service) {
        this.service = service;
    }

    @PostMapping(path = "/modify", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SKExampleResponse> modifySKExample(@RequestBody SKExampleRequest request) {
        try {
            return ResponseEntity.status(OK).body(service.modifySKExample(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(I_AM_A_TEAPOT).build();
        }
    }
}
