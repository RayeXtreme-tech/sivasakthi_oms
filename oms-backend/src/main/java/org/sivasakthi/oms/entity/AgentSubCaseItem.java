package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "a_sub_case_item")
public class AgentSubCaseItem {

    @Id
    @SequenceGenerator(
            name = "a_sub_case_item_id_seq",
            sequenceName = "a_sub_case_item_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "a_sub_case_item_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @Column(nullable = false)
    private double quantity;

    @ManyToOne
    @JoinColumn(name = "sub_case_id", referencedColumnName = "id", nullable = false)
    private AgentSubCase subCase;

    @ManyToOne
    @JoinColumn(name = "company_product_price_id", referencedColumnName = "id", nullable = false)
    private CostPrice costPrice;
}
