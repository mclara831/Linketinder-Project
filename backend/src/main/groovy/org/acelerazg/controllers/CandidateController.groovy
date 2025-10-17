package org.acelerazg.controllers

import org.acelerazg.cli.UI
import org.acelerazg.models.Candidate
import org.acelerazg.models.Endereco
import org.acelerazg.services.CandidateService
import org.acelerazg.services.SkillService
import org.acelerazg.services.EnderecoService

class CandidateController {

    CandidateService candidateService
    EnderecoService addressService
    SkillService skillService

    CandidateController() {
        this.candidateService = new CandidateService()
        this.addressService = new EnderecoService()
        this.skillService = new SkillService()
    }

    void findAll() {
        List<Candidate> candidatos = candidateService.findAll()

        candidatos.each { it ->
            println(it.toString())
            print(addressService.encontrarEnderecoPorID(it.addressId).toString())
            println("\n\tCompetencias: " + skillService.findSkillsByCandidate(it.id).join(", "))
        }
    }

    void create() {
        Candidate candidate = UI.readCandidateInfo()
        Endereco address = UI.readAdress()
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
        Endereco address = UI.readAdress()
        String skills = UI.readSkills()

        updatedCandidate = candidateService.updateByCpf(updatedCandidate, address, skills)
        println(updatedCandidate.toString())
    }

    void delete() {
        String cpf = UI.requestCpf()
        candidateService.deleteByCpf(cpf)
    }
}
