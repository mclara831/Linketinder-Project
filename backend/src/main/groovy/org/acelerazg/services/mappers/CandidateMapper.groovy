package org.acelerazg.services.mappers

import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.CandidateResponseDTO

class CandidateMapper {
    CandidateResponseDTO mapToDto(Candidate candidate, String address, String skills) {
        CandidateResponseDTO candidateResponseDTO = new CandidateResponseDTO()
        candidateResponseDTO.name = candidate.name
        candidateResponseDTO.lastname = candidate.lastname
        candidateResponseDTO.cpf = candidate.cpf
        candidateResponseDTO.email = candidate.email
        candidateResponseDTO.linkedin = candidate.linkedin
        candidateResponseDTO.description = candidate.description
        candidateResponseDTO.dateOfBirth = candidate.dateOfBirth
        candidateResponseDTO.password = candidate.password
        candidateResponseDTO.address = address
        candidateResponseDTO.skills = skills
        return candidateResponseDTO
    }
}
