package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "agent")
public class Agent {

    @Id
    @SequenceGenerator(
            name = "agent_id_seq",
            sequenceName = "agent_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "agent_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;
}
