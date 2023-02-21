package com.java.update.dao;

import java.util.Optional;

public interface SKExampleDAO<T> {
    void increaseSKExampleObjCurrent(Integer id, Integer value);
    void updateObjInSKExample(Integer id, Integer value);
    <S extends T> S save(S entity);
    boolean existsById(Integer id);
    Optional<T> findById(Integer id);
}
