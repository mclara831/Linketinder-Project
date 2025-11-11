package org.acelerazg.services.candidate

import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.candidate.CandidateRequestDTO
import org.acelerazg.models.DTO.candidate.CandidateResponseDTO

interface ICandidateService {
    List<CandidateResponseDTO> findAll()
    CandidateResponseDTO findInfoFromCandidate(Candidate entity)
    Candidate updateData(Candidate existing, Candidate updated, Address address)
    CandidateResponseDTO create(CandidateRequestDTO dto)
    CandidateResponseDTO updateByCpf(String cpf, CandidateRequestDTO dto)
    void deleteByCpf(String cpf)
    boolean cpfValid(String cpf)
}