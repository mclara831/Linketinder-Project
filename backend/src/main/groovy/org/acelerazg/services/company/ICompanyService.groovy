package org.acelerazg.services.company

import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.models.DTO.CompanyResponseDTO

interface ICompanyService {
    Company findById(String empresaId)
    Company findByCnpj(String cnpj)
    List<CompanyResponseDTO> findAll()
    CompanyResponseDTO findInfoFromCompany(Company company)
    Company create(Company company, Address address, String skills)
    Company updateByCnpj(Company company, Address address, String skills)
    void deleteByCnpj(String cnpj)
    Company updateData(Address address, Company existing, Company updated)
}
