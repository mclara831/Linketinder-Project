package org.acelerazg.services.company

import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.models.DTO.company.CompanyConversionResult
import org.acelerazg.models.DTO.company.CompanyRequestDTO
import org.acelerazg.models.DTO.company.CompanyResponseDTO
import org.acelerazg.models.Skill
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

        if (existing == null) throw new Exception("This CNPJ is not registered in the database!")
        return existing
    }

    @Override
    List<CompanyResponseDTO> findAll() {
        List<Company> companies = companyRepository.findAll()
        return companies.collect { findInfoFromCompany(it) }
    }

    @Override
    CompanyResponseDTO findInfoFromCompany(Company company) {
        String address = addressService.findById(company.addressId).toString()
        List<Skill> skills = companySkillService.findSkills(company.id) ?: []
        return companyMapper.mapToDto(company, address, skills)
    }

    @Override
    CompanyResponseDTO create(CompanyRequestDTO dto) {

        try {
            findByCnpj(dto.cnpj)
        }
        catch (Exception e) {}


        CompanyConversionResult result = parseToEntity(dto)
        Company company = result.company
        String skills = result.skills

        String addressId = addressService.find(result.address)
        company.addressId = addressId
        company.id = Utils.generateUUID()

        Company newCompany = companyRepository.create(company)
        companySkillService.addSkillsToEntity(company.id, skills)

        return findInfoFromCompany(newCompany)
    }

    @Override
    CompanyResponseDTO updateByCnpj(String cnpj, CompanyRequestDTO dto) {
        Company existing = new Company()
        try {
            existing = findByCnpj(dto.cnpj)
        }
        catch (Exception e) {
            println(e.getMessage())
            return null
        }

        CompanyConversionResult result = parseToEntity(dto)
        Company company = result.company
        String skills = result.skills
        Address address = result.address

        updateData(address, existing, company)

        companySkillService.removeSkillsFromEntity(existing.id)
        companySkillService.addSkillsToEntity(existing.id, skills)

        Company updated = companyRepository.updateById(existing)
        return findInfoFromCompany(updated)
    }

    @Override
    void deleteByCnpj(String cnpj) {
        Company c = new Company()
        try {
            c = findByCnpj(cnpj)
        }
        catch (Exception e) {
            println(e.getMessage())
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

    CompanyConversionResult parseToEntity(CompanyRequestDTO dto) {
        if (dto == null) return null
        Address address = addressService.formatAddress(dto.address)
        Company company = new Company(dto.name, dto.email, dto.linkedin, dto.description, dto.password, dto.cnpj)

        return new CompanyConversionResult(company, address, dto.skills)
    }
}
