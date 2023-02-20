package com.java.update.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@Repository
public interface SKExampleRepository extends CrudRepository<SKExample, Integer> {

    @Modifying
    @Transactional(isolation = REPEATABLE_READ)
    @Query(value = """
            update sk_example_table set
            obj = cast('{"current": '||(select sum(CAST((obj->>'current') as INT) + :add) from sk_example_table where id = :id)||'}' as JSON)
            where id = :id
            """, nativeQuery = true)
    void executeAddValue(Integer id, Integer add);
}
