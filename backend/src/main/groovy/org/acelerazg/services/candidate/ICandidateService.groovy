package org.acelerazg.services.candidate

import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.CandidateResponseDTO

interface ICandidateService {
    List<CandidateResponseDTO> findAll()
    CandidateResponseDTO findInfoFromCandidate(Candidate entity)
    Candidate updateData(Candidate existing, Candidate updated, Address address)
    Candidate create(Candidate candidate, Address address, String skills)
    Candidate updateByCpf(Candidate candidate, Address address, String skills)
    void deleteByCpf(String cpf)
    boolean cpfValid(String cpf)
}