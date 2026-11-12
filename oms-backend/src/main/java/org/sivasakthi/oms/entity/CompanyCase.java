package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "c_cases")
public class CompanyCase {

    @Id
    @SequenceGenerator(
            name = "c_cases_id_seq",
            sequenceName = "c_cases_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "c_cases_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id", nullable = false)
    private CompanyPurchase purchase;

    @ManyToOne
    @JoinColumn(name = "company_product_price_id", referencedColumnName = "id", nullable = false)
    private CostPrice costPrice;
}
