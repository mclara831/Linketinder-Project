package org.acelerazg.services.mappers

import org.acelerazg.models.Company
import org.acelerazg.models.DTO.CompanyResponseDTO

class CompanyMapper {
    CompanyResponseDTO mapToDto(Company company, String address, String skills) {
        CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO()
        companyResponseDTO.name = company.name
        companyResponseDTO.cnpj = company.cnpj
        companyResponseDTO.email = company.email
        companyResponseDTO.linkedin = company.linkedin
        companyResponseDTO.description = company.description
        companyResponseDTO.password = company.password
        companyResponseDTO.address = address
        companyResponseDTO.skills = skills
        return companyResponseDTO
    }
}
