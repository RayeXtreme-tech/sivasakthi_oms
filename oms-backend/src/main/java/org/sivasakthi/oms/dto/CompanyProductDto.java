package org.sivasakthi.oms.dto;

import lombok.Data;

@Data
public class CompanyProductDto {
    private long id;
    private long productId;
    private String name;
    private String category;
    private double price;
    private int perCase;
}
