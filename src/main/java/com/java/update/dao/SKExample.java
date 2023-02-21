package com.java.update.dao;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "sk_example_table")
@Data
public class SKExample {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private SKExampleObj obj;
}
