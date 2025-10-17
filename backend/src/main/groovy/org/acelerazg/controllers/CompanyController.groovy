package org.acelerazg.controllers

import org.acelerazg.cli.UI
import org.acelerazg.models.Company
import org.acelerazg.models.Endereco
import org.acelerazg.services.CompanyService
import org.acelerazg.services.CompetenciaService
import org.acelerazg.services.EnderecoService

class CompanyController {

    CompanyService companyService
    EnderecoService addressService
    CompetenciaService skillService
    Scanner sc

    CompanyController() {
        this.companyService = new CompanyService()
        this.addressService = new EnderecoService()
        this.skillService = new CompetenciaService()
        sc = new Scanner(System.in)
    }

    void findAll() {
        List<Company> empresas = companyService.findAll()
        empresas.each { it ->
            {
                println(it.toString())
                print(addressService.encontrarEnderecoPorID(it.addressId).toString())
                println("\n\tCompetencias: " + skillService.buscaCompetenciasDaEmpresa(it.id).join(", "))
            }
        }
    }

    void create() {
        Company company = UI.readCompanyInfo()
        Endereco address = UI.readAdress()
        String skills = UI.readSkills()

        company = companyService.create(company, address, skills)
        println(company.toString())
    }

    void update() {
        String cnpj = UI.requestCnpj()

        if (!companyService.cnpjValid(cnpj)) {
            println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
            return
        }

        Company updatedCompany = UI.readCompanyInfo(cnpj)
        Endereco address = UI.readAdress()
        String skills = UI.readSkills()

        Company updated = companyService.updateByCnpj(updatedCompany, address, skills)
        println(updated.toString())
    }

    void delete() {
        String cnpj = UI.requestCnpj()
        companyService.deleteByCnpj(cnpj)
    }
}
