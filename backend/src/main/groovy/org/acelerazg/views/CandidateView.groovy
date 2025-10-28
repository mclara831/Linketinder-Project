package org.acelerazg.views

import org.acelerazg.cli.UI
import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.CandidateResponseDTO
import org.acelerazg.services.candidate.CandidateService

class CandidateView {

    CandidateService candidateService

    CandidateView(CandidateService candidateService) {
        this.candidateService = candidateService
    }

    void findAll() {
        List<CandidateResponseDTO> candidates = candidateService.findAll()

        candidates.each { candidate ->
            println(candidate.toString())
        }
    }

    void create() {
        Candidate candidate = UI.readCandidateInfo()
        Address address = UI.readAdress()
        String skills = UI.readSkills()

        candidate = candidateService.create(candidate, address, skills)
        if (candidate)
            println(candidate.toString())
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

        updatedCandidate = candidateService.updateByCpf(updatedCandidate, address, skills)
        if(updatedCandidate) println(updatedCandidate.toString())
    }

    void delete() {
        String cpf = UI.requestCpf()
        candidateService.deleteByCpf(cpf)
    }
}
