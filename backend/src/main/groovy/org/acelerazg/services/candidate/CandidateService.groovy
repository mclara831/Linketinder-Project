package org.acelerazg.services.candidate

import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.candidate.CandidateConversionResult
import org.acelerazg.models.DTO.candidate.CandidateRequestDTO
import org.acelerazg.models.DTO.candidate.CandidateResponseDTO
import org.acelerazg.models.Skill
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
        List<Skill> skills = candidateSkillService.findSkills(candidate.id) ?: []
        return candidateMapper.mapToDto(candidate, address, skills)
    }

    @Override
    CandidateResponseDTO create(CandidateRequestDTO candidateDTO) {

        if (cpfValid(candidateDTO.cpf)) {
            throw new Exception("[AVISO]: Não é possível utilizar o cpf fornecido!")
        }

        CandidateConversionResult result = parseToEntity(candidateDTO)
        Candidate candidate = result.candidate
        String skills = result.skills

        String addressId = addressService.find(result.address)
        candidate.addressId = addressId
        candidate.id = Utils.generateUUID()

        Candidate newCandidate = candidateRepository.create(candidate)
        candidateSkillService.addSkillsToEntity(newCandidate.id, skills)

        return findInfoFromCandidate(newCandidate)
    }

    @Override
    CandidateResponseDTO updateByCpf(String cpf, CandidateRequestDTO dto) {
        Candidate existing = candidateRepository.findByCpf(cpf)

        if (!existing) {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
            throw new Exception("[AVISO]: Não é possível utilizar o cpf fornecido!")
        }
        dto.cpf = cpf

        CandidateConversionResult result = parseToEntity(dto)
        Candidate candidate = result.candidate
        Address address = result.address
        String skills = result.skills
        existing = updateData(existing, candidate, address)

        candidateSkillService.removeSkillsFromEntity(existing.id)
        candidateSkillService.addSkillsToEntity(existing.id, skills)

        Candidate updated = candidateRepository.updateById(existing)
        return findInfoFromCandidate(updated)
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
            throw new Exception("[AVISO]: Este CPF não está cadastrado em nossa base de dados!")
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

    Candidate findByCpf(String cpf) {
        Candidate c = candidateRepository.findByCpf(cpf)
        return c
    }

    CandidateConversionResult parseToEntity(CandidateRequestDTO dto) {
        if (dto == null) return null
        Address address = addressService.formatAddress(dto.address)
        Candidate candidate = new Candidate(dto.name, dto.lastname, dto.email, dto.linkedin, dto.cpf, dto.dateOfBirth, dto.description, dto.password)

        return new CandidateConversionResult(candidate, address, dto.skills)
    }
}
