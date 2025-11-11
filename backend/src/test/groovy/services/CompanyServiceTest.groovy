package services

import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.models.DTO.company.CompanyRequestDTO
import org.acelerazg.models.DTO.company.CompanyResponseDTO
import org.acelerazg.models.Skill
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.services.company.CompanyService
import org.acelerazg.services.skill.CompanySkillService
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.mappers.CompanyMapper
import spock.lang.Specification

class CompanyServiceTest extends Specification {

    CompanyRepository companyRepository = Mock(CompanyRepository)
    IAddressService addressService = Mock(IAddressService)
    CompanySkillService companySkillService = Mock(CompanySkillService)
    CompanyMapper companyMapper = new CompanyMapper()
    CompanyService companyService = new CompanyService(companyRepository, addressService, companySkillService, companyMapper)

    void "return list of all companies"() {
        given:
        Company[] companies = [
                new Company("Pastel Soft", "pastel@gmail.com", "linkedin.com/in/pastel",
                        "67aaece0-ee39-4e8f-a2a5-5491cdf9860c", "Teste", "teste", "0000000/00000")
        ]

        companyRepository.findAll() >> companies

        when:
        List<CompanyResponseDTO> result = companyService.findAll()

        then:
        result.size() == 1
        result[0].name == "Pastel Soft"
    }

    void "insert new company"() {
        given:
        Company company = new Company("Pastel Soft", "pastel@gmail.com", "linkedin.com/in/pastel",
                "Teste", "teste", "0000000/00000")

        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"

        companyRepository.findByCnpj(_ as String) >> null
        addressService.find(_ as Address) >> "mock-endereco-id"
        companyRepository.create(_ as Company) >> { Company c -> c }
        CompanyRequestDTO dto = new CompanyRequestDTO(company, address, skills)

        when:
        CompanyResponseDTO result = companyService.create(dto)

        then:
        company.name == result.name
        company.cnpj == result.cnpj
    }

    void "update company"() {
        given:
        Company oldCompany = new Company("Pastel Soft", "pastel@gmail.com", "linkedin.com/in/pastel",
                        "Teste", "teste", "0000000/00000")


        Company updatedCompany = new Company("Sanduba Soft", "sanduba@gmail.com", "linkedin.com/in/sanduba",
                "Teste", "teste", "0000000/00000")
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"

        String cnpj = "0000000/00000"
        CompanyRequestDTO dto = new CompanyRequestDTO(updatedCompany, address, skills)


        companyRepository.findByCnpj(_ as String) >> oldCompany
        addressService.find(_ as Address) >> "mock-endereco-id"
        companyRepository.updateById(_ as Company) >> { Company c -> updatedCompany }

        when:
        CompanyResponseDTO result = companyService.updateByCnpj(cnpj, dto)

        then:
        result.name == updatedCompany.name
    }

    void "delete candidate"() {
        given:
        Company[] companies = [
                new Company("Pastel Soft", "pastel@gmail.com", "linkedin.com/in/pastel",
                        "Teste", "teste", "0000000/00000")
        ]
        companyRepository.findByCnpj(_ as String) >> companies[0]
        companyRepository.findAll() >> []

        when:
        companyService.deleteByCnpj(companies[0].cnpj)
        List<CompanyResponseDTO> result = companyService.findAll()

        then:
        result.size() == 0
        1 * companyRepository.deleteByCnpj(_ as String)
    }
}
