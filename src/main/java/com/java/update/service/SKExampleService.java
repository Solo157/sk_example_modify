package com.java.update.service;

import com.java.update.api.SKExampleProcessingType;
import com.java.update.dao.SKExample;
import com.java.update.dao.SKExampleDAO;
import com.java.update.api.SKExampleRequest;
import com.java.update.api.SKExampleResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SKExampleService {

    private final SKExampleDAO<SKExample> repository;

    public SKExampleResponse modifySKExample(SKExampleRequest request, SKExampleProcessingType processingType) throws InterruptedException, NotFoundException {
        if (repository.existsById(request.getId())) {
            increaseSKExampleObjCurrent(request, processingType);
            return new SKExampleResponse(repository.findById(request.getId()).get().getObj().getCurrent());
        } else throw new NotFoundException();
    }

    private synchronized void increaseSKExampleObjCurrent(SKExampleRequest request, SKExampleProcessingType type) throws InterruptedException {
        switch (type) {
            case FOR_UPDATE -> increaseSKExampleObjCurrent_forUpdate(request);
            case ISOLATION -> increaseSKExampleObjCurrent_isolation(request);
        }
    }

    private void increaseSKExampleObjCurrent_isolation(SKExampleRequest request) throws InterruptedException {
        int crashCount = 0;
        int crashCountLimit = 100;
        while (true) {
            try {
                repository.increaseSKExampleObjCurrent(request.getId(), request.getValue());
                break;
            } catch (Exception e) {
                e.printStackTrace();
                if (crashCount++ == crashCountLimit) { throw new InterruptedException(); }
            }
        }
    }

    private void increaseSKExampleObjCurrent_forUpdate(SKExampleRequest request) {
        repository.updateObjInSKExample(request.getId(), request.getValue());
    }
}
