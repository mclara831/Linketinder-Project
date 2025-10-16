package services

import org.acelerazg.models.Candidate
import org.acelerazg.models.Endereco
import org.acelerazg.repositories.CandidateRepository
import org.acelerazg.services.CandidateService
import org.acelerazg.services.CompetenciaService
import org.acelerazg.services.EnderecoService
import spock.lang.Specification

import java.time.LocalDate

class CandidateServiceTest extends Specification {

    def candidateRepository = Mock(CandidateRepository)
    def enderecoService = Mock(EnderecoService)
    def competenciaService = Mock(CompetenciaService)
    def candidatoService = new CandidateService(candidateRepository, enderecoService, competenciaService)

    def "return list of all candidates"() {
        given:
        def candidates = [
                new Candidate("Maria", "Silva", "maria@example.com",
                        "linkedin.com/in/maria", "000.000.000-00", LocalDate.now(),
                        "Test example", "test"),
        ]
        candidateRepository.findAll() >> candidates

        when:
        def result = candidatoService.findAll()

        then:
        result.size() == 1
        result[0].name == "Maria"
    }

    def "insert new candidate"() {
        given:
        Candidate candidate = new Candidate("Joao", "Silva", "joao@example.com",
                "linkedin.com/in/joao", "111.111.111-11", LocalDate.now(), UUID.randomUUID().toString(),
                "Test example", "test")
        Endereco adress = new Endereco("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"

        candidateRepository.findByCpf(_ as String) >> null
        enderecoService.find(_ as Endereco) >> "mock-endereco-id"
        candidateRepository.create(_ as Candidate) >> { Candidate c -> c }
        competenciaService.adicionarCompetenciasACandidato(_, _) >> {}

        when:
        Candidate result = candidatoService.create(candidate, adress, skills)

        then:
        result == candidate
        result.adressId == "mock-endereco-id"
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
        Endereco address = new Endereco("Brasil", "Sao Paulo", "12.345-67")
        String skills = "Java, Angular"

        candidateRepository.findAll() >> candidates
        candidateRepository.findByCpf(_ as String) >> candidates[0]
        enderecoService.find(_ as Endereco) >> "mock-endereco-id"
        competenciaService.removeCompetenciasDoCandidato(_, _) >> {}
        competenciaService.adicionarCompetenciasACandidato(_, _) >> {}
        candidateRepository.updateById(_ as Candidate) >> { Candidate c -> c }

        when:
        candidatoService.updateByCpf(updatedCandidate, address, skills)
        def result = candidatoService.findAll()

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
        candidatoService.deleteByCpf(candidates[0].cpf)
        def result = candidatoService.findAll()

        then:
        result.size() == 0
        1 * candidateRepository.deleteByCpf(_ as String)
    }
}
