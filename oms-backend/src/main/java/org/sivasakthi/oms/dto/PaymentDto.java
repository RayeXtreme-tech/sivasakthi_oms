package org.sivasakthi.oms.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDto {
    private long id;
    private LocalDate date;
    private double amount;
    private String mode;
    private String description;
}
