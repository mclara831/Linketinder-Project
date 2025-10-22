package org.acelerazg.services.company

import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.models.DTO.CompanyResponseDTO
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.mappers.CompanyMapper
import org.acelerazg.services.skill.CompanySkillService
import org.acelerazg.utils.Utils

class CompanyService implements ICompanyService {

    CompanyRepository companyRepository
    IAddressService addressService
    CompanySkillService companySkillService
    CompanyMapper companyMapper

    CompanyService(CompanyRepository repository, IAddressService addressService, CompanySkillService companySkillService, CompanyMapper companyMapper) {
        this.companyRepository = repository
        this.addressService = addressService
        this.companySkillService = companySkillService
        this.companyMapper = companyMapper
    }

    @Override
    Company findById(String empresaId) {
        return companyRepository.findById(empresaId)
    }

    @Override
    Company findByCnpj(String cnpj) {
        Company existing = companyRepository.findByCnpj(cnpj)
        return existing
    }

    @Override
    List<CompanyResponseDTO> findAll() {
        List<Company> companies = companyRepository.findAll()
        return companies.collect{ findInfoFromCompany(it)}
    }

    @Override
    CompanyResponseDTO findInfoFromCompany(Company company) {
        String address = addressService.findById(company.addressId).toString()
        String skills = companySkillService.findSkills(company.id) ?: []
        if (skills) skills.join(", ")
        return companyMapper.mapToDto(company, address, skills)
    }

    @Override
    Company create(Company company, Address address, String skills) {
        if (findByCnpj(company.cnpj)) {
            println "[AVISO]: Não é possível utilizar o cnpj fornecido!"
            return null
        }

        String addressId = addressService.find(address)
        company.addressId = addressId
        company.id = Utils.generateUUID()

        Company newCompany = companyRepository.create(company)
        companySkillService.addSkillsToEntity(company.id, skills)

        return newCompany
    }

    @Override
    Company updateByCnpj(Company company, Address address, String skills) {
        Company existing = companyRepository.findByCnpj(company.cnpj)
        if (!existing) {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
            return null
        }
        updateData(address, existing, company)

        companySkillService.removeSkillsFromEntity(existing.id)
        companySkillService.addSkillsToEntity(existing.id, skills)

        return companyRepository.updateById(existing)
    }

    @Override
    void deleteByCnpj(String cnpj) {
        Company c = companyRepository.findByCnpj(cnpj)
        if (c == null) {
            println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
            return
        }
        companySkillService.removeSkillsFromEntity(c.id)
        companyRepository.deleteByCnpj(cnpj)
        println "[SUCESSO]: Empresa removido com sucesso!"
    }

    @Override
    Company updateData(Address address, Company existing, Company updated) {
        existing.setName(updated.name)
        existing.setEmail(updated.email)
        existing.setLinkedin(updated.linkedin)
        existing.setDescription(updated.description)
        existing.setPassword(updated.password)
        existing.setAddressId(addressService.find(address))

        return existing
    }
}
