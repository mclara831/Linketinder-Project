package org.acelerazg.services

import org.acelerazg.models.Candidate
import org.acelerazg.models.Address
import org.acelerazg.repositories.CandidateRepository
import org.acelerazg.utils.Utils

class CandidateService {

    CandidateRepository candidateRepository
    AddressService addressService
    SkillService skillService

    CandidateService() {
        this(new CandidateRepository(), new AddressService(), new SkillService())
    }

    CandidateService(CandidateRepository candidatoRepository,
                     AddressService addressService,
                     SkillService skillService) {
        this.candidateRepository = candidatoRepository
        this.addressService = addressService
        this.skillService = skillService
    }

    List<Candidate> findAll() {
        return candidateRepository.findAll()
    }

    Candidate create(Candidate candidate, Address address, String competencias) {
        if (cpfValid(candidate.cpf)) {
            println "[AVISO]: Não é possível utilizar o cpf fornecido!"
            return null
        }

        String addressId = addressService.find(address)
        candidate.addressId = addressId
        candidate.id = Utils.generateUUID()

        Candidate newCandidate = candidateRepository.create(candidate)
        skillService.addSkillsToCandidate(newCandidate.id, competencias)

        return newCandidate
    }

    Candidate updateByCpf(Candidate candidate, Address address, String skills) {
        Candidate existing = candidateRepository.findByCpf(candidate.cpf)

        if (!existing) {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
            return null
        }
        existing = updateData(existing, candidate, address)

        skillService.removeSkillsFromCandidate(existing.id)
        skillService.addSkillsToCandidate(existing.id, skills)

        return candidateRepository.updateById(existing)
    }

    void deleteByCpf(String cpf) {
        Candidate c = candidateRepository.findByCpf(cpf)
        if (!c) {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
            return
        }

        skillService.removeSkillsFromCandidate(c.id)
        candidateRepository.deleteByCpf(cpf)

        println "[SUCESSO]: Candidato removido com sucesso!"
    }

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

    boolean cpfValid(String cpf) {
        Candidate c = candidateRepository.findByCpf(cpf)
        return c != null
    }
}
