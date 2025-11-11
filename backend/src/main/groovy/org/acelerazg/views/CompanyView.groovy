package org.acelerazg.views

import org.acelerazg.cli.UI
import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.models.DTO.company.CompanyRequestDTO
import org.acelerazg.models.DTO.company.CompanyResponseDTO
import org.acelerazg.services.company.CompanyService

class CompanyView {

    CompanyService companyService

    CompanyView(CompanyService companyService) {
        this.companyService = companyService
    }

    void findAll() {
        List<CompanyResponseDTO> empresas = companyService.findAll()
        empresas.each { it -> println(it.toString()) }
    }

    void create() {
        try {
            Company company = UI.readCompanyInfo()
            Address address = UI.readAdress()
            String skills = UI.readSkills()

            CompanyRequestDTO dto = new CompanyRequestDTO(company, address, skills)

            CompanyResponseDTO response = companyService.create(dto)
            if (response) println(response.toString())
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

            CompanyRequestDTO dto = new CompanyRequestDTO(updatedCompany, address, skills)
            CompanyResponseDTO response = companyService.updateByCnpj(cnpj, dto)
            if (response) println(response.toString())
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
