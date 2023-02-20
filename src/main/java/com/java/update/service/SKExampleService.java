package com.java.update.service;

import com.java.update.repository.SKExampleRepository;
import com.java.update.api.SKExampleRequest;
import com.java.update.api.SKExampleResponse;
import org.springframework.stereotype.Service;

@Service
public class SKExampleService {

    private final SKExampleRepository repository;

    public SKExampleService(SKExampleRepository repository) {
        this.repository = repository;
    }

    public SKExampleResponse modifySKExample(SKExampleRequest request) throws IllegalArgumentException {
        if (repository.existsById(request.getId())) {
            increaseSKExampleObjCurrent(request.getId(), request.getValue());
            return new SKExampleResponse(repository.findById(request.getId()).get().getObj().getCurrent());
        } else throw new IllegalArgumentException();
    }

    private synchronized void increaseSKExampleObjCurrent(Integer id, Integer value) {
        while (true) {
            try {
                repository.increaseSKExampleObjCurrent(id, value);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
