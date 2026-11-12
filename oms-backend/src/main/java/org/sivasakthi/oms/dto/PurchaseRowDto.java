package org.sivasakthi.oms.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseRowDto {
    private long id;
    private LocalDate date;
    private int noOfCases;
    private double amount;
    private String status;
}
