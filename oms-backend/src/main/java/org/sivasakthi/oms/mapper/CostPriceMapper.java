package org.sivasakthi.oms.mapper;

import org.sivasakthi.oms.dto.CompanyProductDto;
import org.sivasakthi.oms.entity.CostPrice;

public class CostPriceMapper {

    public static CompanyProductDto toDto(CostPrice costPrice) {
        CompanyProductDto productDto = new CompanyProductDto();
        productDto.setId(costPrice.getId());
        productDto.setName(costPrice.getCompanyProduct().getLocalName());
        productDto.setCategory(costPrice.getCompanyProduct().getProduct().getCategory().getName());
        productDto.setPrice(costPrice.getPrice());
        productDto.setPerCase(costPrice.getCompanyProduct().getPerCase());
        productDto.setProductId(costPrice.getCompanyProduct().getProduct().getId());
        return productDto;
    }
}
