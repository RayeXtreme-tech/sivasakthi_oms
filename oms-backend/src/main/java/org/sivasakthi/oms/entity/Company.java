package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "company")
public class Company {

    @Id
    @SequenceGenerator(
            name = "company_id_seq",
            sequenceName = "company_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;
}
