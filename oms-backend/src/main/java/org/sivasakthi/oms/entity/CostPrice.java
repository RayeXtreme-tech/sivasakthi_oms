package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "company_product_price")
public class CostPrice {

    @Id
    @SequenceGenerator(
            name = "company_product_price_id_seq",
            sequenceName = "company_product_price_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_product_price_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_product_id", referencedColumnName = "id", nullable = false)
    private CompanyProduct companyProduct;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean active;
}
