package org.sivasakthi.oms.mapper;

import org.sivasakthi.oms.dto.CompanyDto;
import org.sivasakthi.oms.entity.Company;

public class CompanyMapper {

    public static CompanyDto toDto(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        return companyDto;
    }
}
