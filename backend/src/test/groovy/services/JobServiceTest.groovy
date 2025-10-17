package services

import org.acelerazg.models.Company
import org.acelerazg.models.Address
import org.acelerazg.models.Job
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.repositories.JobRepository
import org.acelerazg.services.AddressService
import org.acelerazg.services.JobService
import org.acelerazg.services.SkillService
import spock.lang.Specification

class JobServiceTest extends Specification {

    def jobRepository = Mock(JobRepository)
    def addressService = Mock(AddressService)
    def skillService = Mock(SkillService)
    def companyRepository = Mock(CompanyRepository)
    def jobService = new JobService(jobRepository, addressService, skillService, companyRepository)

    def "list all job"() {
        given:
        def jobs = [
                new Job("Desenvolvedor Frontend", "Testes de unidade"),
                new Job("Desenvolvedor Backend", "Testes de unidade")
        ]
        jobRepository.findAll() >> jobs

        when:
        def result = jobService.findAll()

        then:
        result.size() == 2
        result[0].name == "Desenvolvedor Frontend"
    }

    def "insert new job"() {
        given:
        Job job = new Job("Desenvolvedor Backend", "Testes de unidade")
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"
        String cnpj = "mock-company-cnpj"
        Company companyMock = new Company("mock-company-id", "mock-name", "mock-email", "mock-company-linkedin", null,
                "mock-company-description", "mock-company-password", cnpj)

        companyRepository.findByCnpj(_ as String) >> companyMock
        addressService.find(_ as Address) >> "mock-endereco-id"
        jobRepository.create(_ as Job) >> job
        skillService.addSkillsToJob(_ as String, _ as String) >> {}

        when:
        Job result = jobService.create(job, address, skills, cnpj)

        then:
        result.name == job.name
    }

    def "update a job"() {
        given:
        Job updated = new Job("mock-job-id-existing", "Desenvolvedor Frontend", "Testes de unidade")
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"

        addressService.find(_ as Address) >> "mock-endereco-id"
        skillService.removeSkillsFromJob(_ as String) >> {}
        skillService.addSkillsToJob(_ as String, _ as String) >> {}
        jobRepository.update(_ as Job) >> updated

        when:
        Job result = jobService.update(updated, address, skills)

        then:
        result.name == updated.name
    }

    def "delete a skill"() {
        given:
        String jobId = "mock-job-id-existing"
        skillService.removeSkillsFromJob(_ as String) >> {}

        when:
        jobService.deleteById(jobId)

        then:
        1 * jobRepository.delete(jobId)
    }
}
