package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.sivasakthi.oms.enums.TransactionMode;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "company_payment")
public class CompanyPayment {

    @Id
    @SequenceGenerator(
            name = "company_payment_id_seq",
            sequenceName = "company_payment_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_payment_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @Column(nullable = false, name = "p_date")
    private LocalDate date;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false, name = "trans_mode")
    @Enumerated(EnumType.STRING)
    private TransactionMode mode;

    @Size(max = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;
}
