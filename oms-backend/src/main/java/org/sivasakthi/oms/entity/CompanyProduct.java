package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "company_product")
public class CompanyProduct {

    @Id
    @SequenceGenerator(
            name = "company_product_id_seq",
            sequenceName = "company_product_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_product_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_agent_id", referencedColumnName = "id", nullable = false)
    private CompanyAgent companyAgent;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(name = "local_name", nullable = false)
    private String localName;

    @Column(name = "per_case", nullable = false)
    private int perCase;
}
