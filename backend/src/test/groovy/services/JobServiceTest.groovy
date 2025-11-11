package services

import org.acelerazg.models.Address
import org.acelerazg.models.Company
import org.acelerazg.models.DTO.job.JobRequestDTO
import org.acelerazg.models.DTO.job.JobResponseDTO
import org.acelerazg.models.Job
import org.acelerazg.models.Skill
import org.acelerazg.repositories.JobRepository
import org.acelerazg.services.job.JobService
import org.acelerazg.services.skill.JobSkillService
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.company.ICompanyService
import org.acelerazg.services.mappers.JobMapper
import spock.lang.Specification

class JobServiceTest extends Specification {

    JobRepository jobRepository = Mock(JobRepository)
    IAddressService addressService = Mock(IAddressService)
    JobSkillService jobSkillService = Mock(JobSkillService)
    ICompanyService companyService = Mock(ICompanyService)
    JobMapper jobMapper = new JobMapper()
    JobService jobService = new JobService(jobRepository, addressService, jobSkillService, companyService, jobMapper)

    void "list all job"() {
        given:
        Job[] jobs = [
                new Job("Desenvolvedor Frontend", "Testes de unidade"),
                new Job("Desenvolvedor Backend", "Testes de unidade")
        ]
        jobRepository.findAll() >> jobs

        when:
        List<JobResponseDTO> result = jobService.findAll()

        then:
        result.size() == 2
        result[0].name == "Desenvolvedor Frontend"
    }

    void "insert new job"() {
        given:
        Job job = new Job("Desenvolvedor Backend", "Testes de unidade")
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"
        String cnpj = "00000000/00000"
        Company companyMock = new Company("mock-company-id", "mock-name", "mock-email", "mock-company-linkedin", null,
                "mock-company-description", "mock-company-password", cnpj)

        JobRequestDTO dto = new JobRequestDTO(job, address, skills,cnpj)

        companyService.findByCnpj(_ as String) >> companyMock
        addressService.find(_ as Address) >> "mock-endereco-id"
        jobRepository.create(_ as Job) >> job

        when:
        JobResponseDTO result = jobService.create(dto)

        then:
        result.name == job.name
    }

    void "update a job"() {
        given:
        String id = "mock-job-id-existing"
        Job updated = new Job("mock-job-id-existing", "Desenvolvedor Frontend", "Testes de unidade")
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"

        JobRequestDTO dto = new JobRequestDTO(updated, address, skills)

        addressService.find(_ as Address) >> "mock-endereco-id"
        jobRepository.update(_ as Job) >> updated

        when:
        JobResponseDTO result = jobService.update(id, dto)

        then:
        result.name == updated.name
    }

    void "delete a skill"() {
        given:
        String jobId = "mock-job-id-existing"

        when:
        jobService.deleteById(jobId)

        then:
        1 * jobRepository.delete(jobId)
    }
}
