package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "c_sub_case")
public class CompanySubCase {

    @Id
    @SequenceGenerator(
            name = "c_sub_case_id_seq",
            sequenceName = "c_sub_case_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "c_sub_case_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id", nullable = false)
    private CompanyPurchase purchase;
}
