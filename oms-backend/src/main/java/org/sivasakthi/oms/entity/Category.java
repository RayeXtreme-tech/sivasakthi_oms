package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "category")
@Entity
public class Category {

    @Id
    @SequenceGenerator(
            name = "category_id_seq",
            sequenceName = "category_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;
}
