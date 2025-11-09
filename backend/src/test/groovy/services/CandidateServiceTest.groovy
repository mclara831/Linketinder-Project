package services

import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.CandidateDTO
import org.acelerazg.repositories.CandidateRepository
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.candidate.CandidateService
import org.acelerazg.services.mappers.CandidateMapper
import org.acelerazg.services.skill.CandidateSkillService
import spock.lang.Specification

import java.time.LocalDate

class CandidateServiceTest extends Specification {

    CandidateRepository candidateRepository = Mock(CandidateRepository)
    CandidateSkillService candidateSkillService = Mock(CandidateSkillService)
    IAddressService addressService = Mock(IAddressService)
    CandidateMapper candidateMapper = new CandidateMapper()
    CandidateService candidateService = new CandidateService(candidateRepository, addressService, candidateSkillService, candidateMapper)

    void "return list of all candidates"() {
        given:
        Candidate[] candidates = [
                new Candidate("Maria", "Silva", "maria@example.com",
                        "linkedin.com/in/maria", "000.000.000-00", LocalDate.now(),
                        "67aaece0-ee39-4e8f-a2a5-5491cdf9860c", "Test example", "test")
        ]
        candidateRepository.findAll() >> candidates

        when:
        List<CandidateDTO> result = candidateService.findAll()

        then:
        result.size() == 1
        result[0].name == "Maria"
    }

    void "insert new candidate"() {
        given:
        Candidate candidate = new Candidate("Joao", "Silva", "joao@example.com",
                "linkedin.com/in/joao", "111.111.111-11", LocalDate.now(), UUID.randomUUID().toString(),
                "Test example", "test")
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"
        CandidateDTO expectedResult = new CandidateDTO(candidate, address, skills)

        candidateRepository.findByCpf(_ as String) >> null
        addressService.find(_ as Address) >> "mock-endereco-id"
        candidateRepository.create(_ as Candidate) >> { Candidate c -> c }
        candidateSkillService.addSkillsToEntity(_ as String, _ as String) >> {}

        when:
        CandidateDTO result = candidateService.create(expectedResult)

        then:
        result.cpf == expectedResult.cpf
    }

    void "update candidate"() {
        given:
        Candidate oldCandidate = new Candidate("Maria", "Silva", "maria@example.com",
                "linkedin.com/in/maria", "111.111.111-02", LocalDate.now(),
                "Test example", "test")


        String cpf = "111.111.111-02"
        Candidate updatedCandidate = new Candidate("Joao", "Silva", "joao@example.com",
                "linkedin.com/in/joao", "111.111.111-02", LocalDate.now(), UUID.randomUUID().toString(),
                "Test example", "test")
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"

        CandidateDTO expectedResult = new CandidateDTO(updatedCandidate, address, skills)

        candidateRepository.findByCpf(_ as String) >> oldCandidate
        addressService.find(_ as Address) >> "mock-endereco-id"
        candidateSkillService.removeSkillsFromEntity(_ as String) >> {}
        candidateSkillService.addSkillsToEntity(_ as String, _ as String) >> {}
        candidateRepository.updateById(_ as Candidate) >> { Candidate c -> updatedCandidate }

        when:
        CandidateDTO result = candidateService.updateByCpf(cpf, expectedResult)

        then:
        result.name == "Joao"
    }

    void "delete candidate"() {
        given:
        Candidate[] candidates = [
                new Candidate("Maria", "Silva", "maria@example.com", "linkedin.com/in/maria", "111.111.111-02", LocalDate.now(), "Test example", "test"),
        ]
        candidateRepository.findByCpf(_ as String) >> candidates[0]
        candidateRepository.findAll() >> []

        when:
        candidateService.deleteByCpf(candidates[0].cpf)
        List<CandidateDTO> result = candidateService.findAll()

        then:
        result.size() == 0
        1 * candidateRepository.deleteByCpf(_ as String)
    }
}
