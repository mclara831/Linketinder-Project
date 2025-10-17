package org.acelerazg.controllers

import org.acelerazg.cli.UI
import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.services.AddressService
import org.acelerazg.services.CandidateService
import org.acelerazg.services.SkillService

class CandidateController {

    CandidateService candidateService
    AddressService addressService
    SkillService skillService

    CandidateController() {
        this.candidateService = new CandidateService()
        this.addressService = new AddressService()
        this.skillService = new SkillService()
    }

    void findAll() {
        List<Candidate> candidatos = candidateService.findAll()

        candidatos.each { it ->
            println(it.toString())
            print(addressService.findById(it.addressId).toString())
            println("\n\tCompetencias: " + skillService.findSkillsByCandidate(it.id).join(", "))
        }
    }

    void create() {
        Candidate candidate = UI.readCandidateInfo()
        Address address = UI.readAdress()
        String skills = UI.readSkills()

        if (candidateService.cpfValid(candidate.cpf)) {
            println "[AVISO]: Este CPF já está cadastrado!"
            return
        }

        candidate = candidateService.create(candidate, address, skills)
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
        println(updatedCandidate.toString())
    }

    void delete() {
        String cpf = UI.requestCpf()
        candidateService.deleteByCpf(cpf)
    }
}
