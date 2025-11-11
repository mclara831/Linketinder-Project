package org.acelerazg.views

import org.acelerazg.cli.UI
import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.candidate.CandidateRequestDTO
import org.acelerazg.models.DTO.candidate.CandidateResponseDTO
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
        try {
            Candidate candidate = UI.readCandidateInfo()
            Address address = UI.readAdress()
            String skills = UI.readSkills()

            CandidateRequestDTO dto = new CandidateRequestDTO(candidate, address, skills)

            CandidateResponseDTO response = candidateService.create(dto)
            if (response) println(response.toString())
        } catch (Exception e) {
            println(e.getMessage())
        }
    }

    void update() {
        try {
            String cpf = UI.requestCpf()

            if (!candidateService.cpfValid(cpf)) {
                println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
                return
            }

            Candidate updatedCandidate = UI.readCandidateInfo(cpf)
            Address address = UI.readAdress()
            String skills = UI.readSkills()

            CandidateRequestDTO dto = new CandidateRequestDTO(updatedCandidate, address, skills)

            CandidateResponseDTO response = candidateService.updateByCpf(cpf, dto)
            if (response) println(response.toString())
        } catch (Exception e) {
            println(e.getMessage())
        }
    }

    void delete() {
        String cpf = UI.requestCpf()
        candidateService.deleteByCpf(cpf)
    }
}
