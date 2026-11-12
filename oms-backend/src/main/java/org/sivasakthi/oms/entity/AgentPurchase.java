package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.sivasakthi.oms.enums.PurchaseStatus;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "agent_purchase")
public class AgentPurchase {

    @Id
    @SequenceGenerator(
            name = "agent_purchase_id_seq",
            sequenceName = "agent_purchase_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "agent_purchase_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @Column(nullable = false, name = "p_date")
    private LocalDate date;

    @Column(nullable = false, name = "no_of_cases")
    private int noOfCases;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "p_status")
    private PurchaseStatus status;

    @Size(max = 1000, message = "description length should between 0 and 1000")
    private String description;

    @Column(nullable = false, name = "transport_charge")
    private double transportCharge;

    @Column(nullable = false, name = "transport_charge_local")
    private double transportChargeLocal;

    @Column(nullable = false, name = "labour_charge")
    private double labourCharge;

    @ManyToOne
    @JoinColumn(name = "agent_id", referencedColumnName = "id", nullable = false)
    private Agent agent;
}
