package org.sivasakthi.oms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "company_agent")
public class CompanyAgent {

    @Id
    @SequenceGenerator(
            name = "company_agent_id_seq",
            sequenceName = "company_agent_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_agent_id_seq"
    )
    @Column(updatable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "agent_id", referencedColumnName = "id")
    private Agent agent;
}
