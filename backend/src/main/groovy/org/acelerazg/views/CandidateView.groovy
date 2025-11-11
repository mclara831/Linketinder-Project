package org.acelerazg.views

import org.acelerazg.cli.UI
import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.candidate.CandidateDTO
import org.acelerazg.services.candidate.CandidateService

class CandidateView {

    CandidateService candidateService

    CandidateView(CandidateService candidateService) {
        this.candidateService = candidateService
    }

    void findAll() {
        List<CandidateDTO> candidates = candidateService.findAll()

        candidates.each { candidate ->
            println(candidate.toString())
        }
    }

    void create() {
        Candidate candidate = UI.readCandidateInfo()
        Address address = UI.readAdress()
        String skills = UI.readSkills()

        CandidateDTO dto = new CandidateDTO(candidate, address, skills)

        dto = candidateService.create(dto)
        if (dto) println(dto.toString())
    }

    void update() {
        String cpf = UI.requestCpf()

        if (!candidateService.cpfValid(cpf)) {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
            return
        }

        Candidate updatedCandidate = UI.readCandidateInfo(cpf)
        Address address = UI.readAdress()
        String skills = UI.readSkills()

        CandidateDTO dto = new CandidateDTO(updatedCandidate, address, skills)

        CandidateDTO response = candidateService.updateByCpf(cpf, dto)
        if (response) println(response.toString())
    }

    void delete() {
        String cpf = UI.requestCpf()
        candidateService.deleteByCpf(cpf)
    }
}
