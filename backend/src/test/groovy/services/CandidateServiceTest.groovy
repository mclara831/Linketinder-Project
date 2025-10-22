package services

import org.acelerazg.models.Candidate
import org.acelerazg.models.Address
import org.acelerazg.repositories.CandidateRepository
import org.acelerazg.services.candidate.CandidateService
import org.acelerazg.services.skill.CandidateSkillService
import org.acelerazg.services.skill.SkillService
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.mappers.CandidateMapper
import spock.lang.Specification

import java.time.LocalDate

class CandidateServiceTest extends Specification {

    def candidateRepository = Mock(CandidateRepository)
    def candidateSkillService = Mock(CandidateSkillService)
    def addressService = Mock(IAddressService)
    def skillService = Mock(SkillService)
    def candidateMapper = new CandidateMapper()
    def candidateService = new CandidateService(candidateRepository, addressService, candidateSkillService, candidateMapper)

    def "return list of all candidates"() {
        given:
        def candidates = [
                new Candidate("Maria", "Silva", "maria@example.com",
                        "linkedin.com/in/maria", "000.000.000-00", LocalDate.now(),
                        "67aaece0-ee39-4e8f-a2a5-5491cdf9860c", "Test example", "test")
        ]
        candidateRepository.findAll() >> candidates

        when:
        def result = candidateService.findAll()

        then:
        result.size() == 1
        result[0].name == "Maria"
    }

    def "insert new candidate"() {
        given:
        Candidate candidate = new Candidate("Joao", "Silva", "joao@example.com",
                "linkedin.com/in/joao", "111.111.111-11", LocalDate.now(), UUID.randomUUID().toString(),
                "Test example", "test")
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"

        candidateRepository.findByCpf(_ as String) >> null
        addressService.find(_ as Address) >> "mock-endereco-id"
        candidateRepository.create(_ as Candidate) >> { Candidate c -> c }
        skillService.addSkillsToCandidate(_, _) >> {}

        when:
        Candidate result = candidateService.create(candidate, address, skills)

        then:
        result == candidate
        result.addressId == "mock-endereco-id"
    }

    def "update candidate"() {
        given:
        def candidates = [
                new Candidate("Maria", "Silva", "maria@example.com",
                        "linkedin.com/in/maria", "111.111.111-02", LocalDate.now(),
                        "Test example", "test"),
        ]

        Candidate updatedCandidate = new Candidate("Joao", "Silva", "joao@example.com",
                "linkedin.com/in/joao", "111.111.111-02", LocalDate.now(), UUID.randomUUID().toString(),
                "Test example", "test")
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"

        candidateRepository.findAll() >> candidates
        candidateRepository.findByCpf(_ as String) >> candidates[0]
        addressService.find(_ as Address) >> "mock-endereco-id"
        candidateService.updateData(_ as Candidate, _ as Candidate, _ as Address) >> updatedCandidate
        skillService.removeSkillsFromCandidate(_, _) >> {}
        skillService.addSkillsToCandidate(_, _) >> {}
        candidateRepository.updateById(_ as Candidate) >> { Candidate c -> c }

        when:
        candidateService.updateByCpf(updatedCandidate, address, skills)
        def result = candidateService.findAll()

        then:
        result.size() == 1
        result[0].cpf == updatedCandidate.cpf
    }

    def "delete candidate"() {
        given:
        def candidates = [
                new Candidate("Maria", "Silva", "maria@example.com", "linkedin.com/in/maria", "111.111.111-02", LocalDate.now(), "Test example", "test"),
        ]
        candidateRepository.findByCpf(_ as String) >> candidates[0]
        candidateRepository.findAll() >> []

        when:
        candidateService.deleteByCpf(candidates[0].cpf)
        def result = candidateService.findAll()

        then:
        result.size() == 0
        1 * candidateRepository.deleteByCpf(_ as String)
    }
}
