package org.sivasakthi.oms.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentsDto {
    private double totalAmount;
    private List<PaymentDto> payments;
}
