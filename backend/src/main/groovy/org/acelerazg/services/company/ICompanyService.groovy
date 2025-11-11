package org.acelerazg.services.company

import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.models.DTO.company.CompanyRequestDTO
import org.acelerazg.models.DTO.company.CompanyResponseDTO

interface ICompanyService {
    Company findById(String empresaId)
    Company findByCnpj(String cnpj)
    List<CompanyResponseDTO> findAll()
    CompanyResponseDTO findInfoFromCompany(Company company)
    CompanyResponseDTO create(CompanyRequestDTO dto)
    CompanyResponseDTO updateByCnpj(String cnpj, CompanyRequestDTO dto)
    void deleteByCnpj(String cnpj)
    Company updateData(Address address, Company existing, Company updated)
}
