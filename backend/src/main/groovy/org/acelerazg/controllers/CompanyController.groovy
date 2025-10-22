package org.acelerazg.controllers

import org.acelerazg.cli.UI
import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.models.DTO.CompanyResponseDTO
import org.acelerazg.services.company.CompanyService

class CompanyController {

    CompanyService companyService

    CompanyController(CompanyService companyService) {
        this.companyService = companyService
    }

    void findAll() {
        List<CompanyResponseDTO> empresas = companyService.findAll()
        empresas.each { it -> println(it.toString()) }
    }

    void create() {
        Company company = UI.readCompanyInfo()
        Address address = UI.readAdress()
        String skills = UI.readSkills()

        company = companyService.create(company, address, skills)
        if (company)println(company.toString())
    }

    void update() {
        String cnpj = UI.requestCnpj()

        if (companyService.findByCnpj(cnpj) == null) {
            println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
            return
        }

        Company updatedCompany = UI.readCompanyInfo(cnpj)
        Address address = UI.readAdress()
        String skills = UI.readSkills()

        Company updated = companyService.updateByCnpj(updatedCompany, address, skills)
        if (updated) println(updated.toString())
    }

    void delete() {
        String cnpj = UI.requestCnpj()
        companyService.deleteByCnpj(cnpj)
    }
}
