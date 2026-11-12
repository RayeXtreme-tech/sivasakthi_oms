package org.sivasakthi.oms.dto;

import lombok.Data;

@Data
public class CompanyDto {
    private long id;
    private String name;
    private long agentId;
    private int totalProducts;
    private int totalCases;
    private double totalPurchases;
    private double balance;
}
