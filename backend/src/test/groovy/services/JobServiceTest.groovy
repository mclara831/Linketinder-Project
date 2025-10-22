package services

import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.models.Job
import org.acelerazg.repositories.JobRepository
import org.acelerazg.services.job.JobService
import org.acelerazg.services.skill.JobSkillService
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.company.ICompanyService
import org.acelerazg.services.mappers.JobMapper
import spock.lang.Specification

class JobServiceTest extends Specification {

    def jobRepository = Mock(JobRepository)
    def addressService = Mock(IAddressService)
    def jobSkillService = Mock(JobSkillService)
    def companyService = Mock(ICompanyService)
    def jobMapper = new JobMapper()
    def jobService = new JobService(jobRepository, addressService, jobSkillService, companyService, jobMapper)

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
        String cnpj = "00000000/00000"
        Company companyMock = new Company("mock-company-id", "mock-name", "mock-email", "mock-company-linkedin", null,
                "mock-company-description", "mock-company-password", cnpj)

        companyService.findByCnpj(_ as String) >> companyMock
        addressService.find(_ as Address) >> "mock-endereco-id"
        jobRepository.create(_ as Job) >> job

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
        jobRepository.update(_ as Job) >> updated

        when:
        Job result = jobService.update(updated, address, skills)

        then:
        result.name == updated.name
    }

    def "delete a skill"() {
        given:
        String jobId = "mock-job-id-existing"

        when:
        jobService.deleteById(jobId)

        then:
        1 * jobRepository.delete(jobId)
    }
}
