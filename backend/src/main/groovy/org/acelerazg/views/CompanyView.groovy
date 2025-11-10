package org.acelerazg.views

import org.acelerazg.cli.UI
import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.models.DTO.CompanyDTO
import org.acelerazg.services.company.CompanyService

class CompanyView {

    CompanyService companyService

    CompanyView(CompanyService companyService) {
        this.companyService = companyService
    }

    void findAll() {
        List<CompanyDTO> empresas = companyService.findAll()
        empresas.each { it -> println(it.toString()) }
    }

    void create() {
        try {
            Company company = UI.readCompanyInfo()
            Address address = UI.readAdress()
            String skills = UI.readSkills()

            CompanyDTO dto = new CompanyDTO(company, address, skills)

            dto = companyService.create(dto)
            if (dto) println(dto.toString())
        } catch (Exception e) {
            println(e.getMessage())
        }
    }

    void update() {
        try {
            String cnpj = UI.requestCnpj()

            if (companyService.findByCnpj(cnpj) == null) {
                println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
                return
            }

            Company updatedCompany = UI.readCompanyInfo(cnpj)
            Address address = UI.readAdress()
            String skills = UI.readSkills()

            CompanyDTO dto = new CompanyDTO(updatedCompany, address, skills)
            dto = companyService.updateByCnpj(cnpj, dto)
            if (dto) println(dto.toString())
        } catch (Exception e) {
            println(e.getMessage())
        }
    }

    void delete() {
        try {
            String cnpj = UI.requestCnpj()
            companyService.deleteByCnpj(cnpj)
        } catch (Exception e) {
            println(e.getMessage())
        }
    }
}
