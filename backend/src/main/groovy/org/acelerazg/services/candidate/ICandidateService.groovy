package org.acelerazg.services.candidate

import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.CandidateDTO

interface ICandidateService {
    List<CandidateDTO> findAll()
    CandidateDTO findInfoFromCandidate(Candidate entity)
    Candidate updateData(Candidate existing, Candidate updated, Address address)
    CandidateDTO create(CandidateDTO dto)
    CandidateDTO updateByCpf(String cpf, CandidateDTO dto)
    void deleteByCpf(String cpf)
    boolean cpfValid(String cpf)
}