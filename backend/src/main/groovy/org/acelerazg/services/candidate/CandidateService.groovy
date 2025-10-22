package org.acelerazg.services.candidate

import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.CandidateResponseDTO
import org.acelerazg.repositories.CandidateRepository
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.mappers.CandidateMapper
import org.acelerazg.services.skill.CandidateSkillService
import org.acelerazg.utils.Utils

class CandidateService implements ICandidateService {

    CandidateRepository candidateRepository
    IAddressService addressService
    CandidateSkillService candidateSkillService
    CandidateMapper candidateMapper

    CandidateService(CandidateRepository candidatoRepository,
                     IAddressService addressService,
                     CandidateSkillService candidateSkillService,
                     CandidateMapper candidateMapper) {
        this.candidateRepository = candidatoRepository
        this.addressService = addressService
        this.candidateSkillService = candidateSkillService
        this.candidateMapper = candidateMapper
    }

    @Override
    List<CandidateResponseDTO> findAll() {
        List<Candidate> candidates = candidateRepository.findAll()
        return candidates.collect { findInfoFromCandidate(it) }
    }

    @Override
    CandidateResponseDTO findInfoFromCandidate(Candidate candidate) {
        String address = addressService.findById(candidate.addressId).toString()
        String skills =  candidateSkillService.findSkills(candidate.id) ?: []
        if (skills) skills.join(", ")
        return candidateMapper.mapToDto(candidate, address, skills)
    }

    @Override
    Candidate create(Candidate candidate, Address address, String skills) {
        if (cpfValid(candidate.cpf)) {
            println "[AVISO]: Não é possível utilizar o cpf fornecido!"
            return null
        }

        String addressId = addressService.find(address)
        candidate.addressId = addressId
        candidate.id = Utils.generateUUID()

        Candidate newCandidate = candidateRepository.create(candidate)
          candidateSkillService.addSkillsToEntity(newCandidate.id, skills)

        return newCandidate
    }

    @Override
    Candidate updateByCpf(Candidate candidate, Address address, String skills) {
        Candidate existing = candidateRepository.findByCpf(candidate.cpf)

        if (!existing) {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
            return null
        }
        existing = updateData(existing, candidate, address)

          candidateSkillService.removeSkillsFromEntity(existing.id)
          candidateSkillService.addSkillsToEntity(existing.id, skills)

        return candidateRepository.updateById(existing)
    }

    @Override
    Candidate updateData(Candidate existing, Candidate updated, Address address) {
        existing.setName(updated.name)
        existing.setLastname(updated.lastname)
        existing.setEmail(updated.email)
        existing.setLinkedin(updated.linkedin)
        existing.setDateOfBirth(updated.dateOfBirth)
        existing.setDescription(updated.description)
        existing.setPassword(updated.password)
        existing.setAddressId(addressService.find(address))
        return existing
    }

    @Override
    void deleteByCpf(String cpf) {
        Candidate c = candidateRepository.findByCpf(cpf)
        if (!c) {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
            return
        }

          candidateSkillService.removeSkillsFromEntity(c.id)
        candidateRepository.deleteByCpf(cpf)

        println "[SUCESSO]: Candidato removido com sucesso!"
    }

    @Override
    boolean cpfValid(String cpf) {
        Candidate c = candidateRepository.findByCpf(cpf)
        return c != null
    }
}
