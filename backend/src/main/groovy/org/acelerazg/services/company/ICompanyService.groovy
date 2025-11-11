package org.acelerazg.services.company

import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.models.DTO.company.CompanyDTO

interface ICompanyService {
    Company findById(String empresaId)
    Company findByCnpj(String cnpj)
    List<CompanyDTO> findAll()
    CompanyDTO findInfoFromCompany(Company company)
    CompanyDTO create(CompanyDTO dto)
    CompanyDTO updateByCnpj(String cnpj, CompanyDTO dto)
    void deleteByCnpj(String cnpj)
    Company updateData(Address address, Company existing, Company updated)
}
