package org.acelerazg.services

import org.acelerazg.models.Company
import org.acelerazg.models.Endereco
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.utils.Utils

class CompanyService {

    CompanyRepository companyRepository
    EnderecoService addressService
    SkillService skillService

    CompanyService() {
        this(new CompanyRepository(), new EnderecoService(), new SkillService())
    }

    CompanyService(CompanyRepository repository, EnderecoService addressService, SkillService skillService) {
        this.companyRepository = repository
        this.addressService = addressService
        this.skillService = skillService
    }

    List<Company> findAll() {
        return companyRepository.findAll()
    }

    Company findById(String empresaId) {
        return companyRepository.findById(empresaId)
    }

    Company create(Company company, Endereco address, String skills) {
        if (cnpjValid(company.cnpj)) {
            println "[AVISO]: Não é possível utilizar o cnpj fornecido!"
            return null
        }

        String addressId = addressService.find(address)
        company.addressId = addressId
        company.id = Utils.generateUUID()

        Company newCompany = companyRepository.create(company)
        skillService.addSkillsToCompany(company.id, skills)

        return newCompany
    }

    Company updateByCnpj(Company company, Endereco address, String skills) {
        Company existing = companyRepository.findByCnpj(company.cnpj)
        if (!existing) {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
            return null
        }
        updateData(address, existing, company)

        skillService.removeSkillsFromCompany(existing.id)
        skillService.addSkillsToCompany(existing.id, skills)

        return companyRepository.updateById(existing)
    }


    void deleteByCnpj(String cnpj) {
        Company c = companyRepository.findByCnpj(cnpj)
        if (c == null) {
            println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
            return
        }
        skillService.removeSkillsFromCompany(c.id)
        companyRepository.deleteByCnpj(cnpj)
        println "[SUCESSO]: Empresa removido com sucesso!"
    }

    boolean cnpjValid(String cnpj) {
        Company existing = companyRepository.findByCnpj(cnpj)
        return existing != null
    }

    Company updateData(Endereco address, Company existing, Company updated) {
        existing.setName(updated.name)
        existing.setEmail(updated.email)
        existing.setLinkedin(updated.linkedin)
        existing.setDescription(updated.description)
        existing.setPassword(updated.password)
        existing.setAddressId(addressService.find(address))

        return existing
    }
}
