package com.java.update.api;

import com.java.update.service.SKExampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/modify")
public class SKExampleApi {

    private final SKExampleService service;

    public SKExampleApi(SKExampleService service) {
        this.service = service;
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<SKExampleResponse> modifySKExample(@RequestBody SKExampleRequest request) {
        try {
            return ResponseEntity.status(OK).body(service.modifySKExample(request));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(I_AM_A_TEAPOT).build();
        }
    }
}
