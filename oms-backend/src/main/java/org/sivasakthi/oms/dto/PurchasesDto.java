package org.sivasakthi.oms.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchasesDto {
    private int totalCases;
    private double totalAmount;
    private List<PurchaseRowDto> purchases;
}
