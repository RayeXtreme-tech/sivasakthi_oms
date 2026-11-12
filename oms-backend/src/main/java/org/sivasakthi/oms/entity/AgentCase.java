package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cases")
public class AgentCase {

    @Id
    @SequenceGenerator(
            name = "a_cases_id_seq",
            sequenceName = "a_cases_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "a_cases_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id", nullable = false)
    private AgentPurchase purchase;

    @ManyToOne
    @JoinColumn(name = "company_product_price_id", referencedColumnName = "id", nullable = false)
    private CostPrice costPrice;
}
