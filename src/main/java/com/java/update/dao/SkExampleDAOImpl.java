package com.java.update.dao;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@Component
@AllArgsConstructor
public class SkExampleDAOImpl implements SKExampleDAO<SKExample> {

    private final SKExampleRepository repository;

    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public void increaseSKExampleObjCurrent(Integer id, Integer value) {
        repository.increaseSKExampleObjCurrent(id, value);
    }

    @Transactional
    @Override
    public void updateObjInSKExample(Integer id, Integer value) {
        Gson gson = new Gson();
        SKExample skExample = repository.setWriteLockSKExample(id);
        Integer objCurrent = skExample.getObj().getCurrent();
        skExample.setObj(
                new SKExampleObj(objCurrent + value)
        );

        String str = gson.toJson(new SKExampleObj(objCurrent + value));
        repository.updateObjInSKExample(id, str);
    }

    @Override
    public <S extends SKExample> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<SKExample> findById(Integer id) {
        return repository.findById(id);
    }
}
