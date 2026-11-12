package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "c_sub_case_item")
public class CompanySubCaseItem {

    @Id
    @SequenceGenerator(
            name = "c_sub_case_item_id_seq",
            sequenceName = "c_sub_case_item_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "c_sub_case_item_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @Column(nullable = false)
    private double quantity;

    @ManyToOne
    @JoinColumn(name = "sub_case_id", referencedColumnName = "id", nullable = false)
    private CompanySubCase subCase;

    @ManyToOne
    @JoinColumn(name = "company_product_price_id", referencedColumnName = "id", nullable = false)
    private CostPrice costPrice;
}
