package services

import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.services.company.CompanyService
import org.acelerazg.services.skill.CompanySkillService
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.mappers.CompanyMapper
import spock.lang.Specification

class CompanyServiceTest extends Specification {

    def companyRepository = Mock(CompanyRepository)
    def addressService = Mock(IAddressService)
    def companySkillService = Mock(CompanySkillService)
    def companyMapper = new CompanyMapper()
    def companyService = new CompanyService(companyRepository, addressService, companySkillService, companyMapper)

    def "return list of all companies"() {
        given:
        def companies = [
                new Company("Pastel Soft", "pastel@gmail.com", "linkedin.com/in/pastel",
                        "67aaece0-ee39-4e8f-a2a5-5491cdf9860c", "Teste", "teste", "0000000/00000")
        ]


        companyRepository.findAll() >> companies

        when:
        def result = companyService.findAll()

        then:
        result.size() == 1
        result[0].name == "Pastel Soft"
    }

    def "insert new company"() {
        given:
        Company company = new Company("Pastel Soft", "pastel@gmail.com", "linkedin.com/in/pastel",
                "Teste", "teste", "0000000/00000")

        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"

        companyRepository.findByCnpj(_ as String) >> null
        addressService.find(_ as Address) >> "mock-endereco-id"
        companyRepository.create(_ as Company) >> { Company c -> c }

        when:
        Company result = companyService.create(company, address, skills)

        then:
        result == company
        result.addressId == "mock-endereco-id"
    }

    def "update company"() {
        given:
        def companies = [
                new Company("Pastel Soft", "pastel@gmail.com", "linkedin.com/in/pastel",
                        "Teste", "teste", "0000000/00000")
        ]

        Company updatedCompany = new Company("Sanduba Soft", "sanduba@gmail.com", "linkedin.com/in/sanduba",
                "Teste", "teste", "0000000/00000")
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"

        companyRepository.findAll() >> companies
        companyRepository.findByCnpj(_ as String) >> companies[0]
        addressService.find(_ as Address) >> "mock-endereco-id"
        companyService.updateData(_ as Address, _ as Company, _ as Company) >> updatedCompany
        companyRepository.updateById(_ as Company) >> { Company c -> c }

        when:
        companyService.updateByCnpj(updatedCompany, address, skills)
        def result = companyService.findAll()

        then:
        result.size() == 1
        result[0].cnpj == updatedCompany.cnpj
    }

    def "delete candidate"() {
        given:
        def companies = [
                new Company("Pastel Soft", "pastel@gmail.com", "linkedin.com/in/pastel",
                        "Teste", "teste", "0000000/00000")
        ]
        companyRepository.findByCnpj(_ as String) >> companies[0]
        companyRepository.findAll() >> []

        when:
        companyService.deleteByCnpj(companies[0].cnpj)
        def result = companyService.findAll()

        then:
        result.size() == 0
        1 * companyRepository.deleteByCnpj(_ as String)
    }
}
