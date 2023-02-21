package com.java.update.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SKExampleRepository extends CrudRepository<SKExample, Integer> {

    @Modifying
    @Query(value = """
            update sk_example_table set
            obj = cast('{"current": '||(select sum(CAST((obj->>'current') as INT) + :value) from sk_example_table where id = :id)||'}' as JSON)
            where id = :id
            """, nativeQuery = true)
    void increaseSKExampleObjCurrent(Integer id, Integer value);

    @Query(value = """
            select * from sk_example_table where id = :id FOR UPDATE
            """, nativeQuery = true)
    SKExample setWriteLockSKExample(Integer id);

    @Modifying
    @Query(value = """
            update sk_example_table set obj = cast(:obj as JSON) where id = :id
            """, nativeQuery = true)
    void updateObjInSKExample(Integer id, String obj);
}
