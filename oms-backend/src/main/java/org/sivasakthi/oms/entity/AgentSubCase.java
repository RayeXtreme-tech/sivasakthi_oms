package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "a_sub_case")
public class AgentSubCase {

    @Id
    @SequenceGenerator(
            name = "a_sub_case_id_seq",
            sequenceName = "a_sub_case_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "a_sub_case_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id", nullable = false)
    private AgentPurchase purchase;
}
