package org.acelerazg.controllers

import org.acelerazg.cli.UI
import org.acelerazg.models.Address
import org.acelerazg.models.Job
import org.acelerazg.services.AddressService
import org.acelerazg.services.CompanyService
import org.acelerazg.services.JobService
import org.acelerazg.services.SkillService

class JobController {

    JobService jobService
    AddressService addressService
    CompanyService companyService
    SkillService skillService

    JobController() {
        this.jobService = new JobService()
        this.addressService = new AddressService()
        this.companyService = new CompanyService()
        this.skillService = new SkillService()
    }

    void findAll() {
        List<Job> vagas = jobService.findAll()
        vagas.forEach { it ->
            {
                println(it.toString())
                print(addressService.findById(it.addressId).toString())
                println("\n\tCompetencias: " + skillService.findSkillsByJob(it.id).join(", "))
            }
        }
    }

    void create() {
        String cnpj = UI.requestCnpj()

        if (!companyService.cnpjValid(cnpj)) {
            println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
            return
        }

        Job job = UI.readJobInfo()
        Address address = UI.readAdress()
        String skills = UI.readSkills()

        Job created = jobService.create(job, address, skills, cnpj)
        println("[CREATED]: ${created.toString()}")
    }

    void update() {
        List<Job> jobs = jobService.findAll()
        printJobList(jobs)

        int option = UI.readInt()
        isValidOption(option, jobs.size())

        Job job = UI.readJobInfo()
        Address address = UI.readAdress()
        String skills = UI.readSkills()

        job.id = jobs[option - 1].id

        Job result = jobService.update(job, address, skills)
        println("[UPDATED]: ${result.toString()}")
    }

    void delete() {
        List<Job> jobs = jobService.findAll()
        printJobList(jobs)

        int option = UI.readInt()
        isValidOption(option, jobs.size())

        jobService.deleteById(jobs[option - 1].id)
    }

    void findJobFromACompany() {

        String cnpj = UI.requestCnpj()
        if (!companyService.cnpjValid(cnpj)) {
            println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
            return
        }

        List<Job> jobs = jobService.findJobFromACompany(cnpj)
        if (jobs.isEmpty()) {
            println("[AVISO]: Não foram encontradas vagas cadastradas nesse CNPJ!")
            return
        }

        jobs.forEach { it ->
            {
                println(it.toString())
                println("\tEmpresa = " + companyService.findById(it.companyId).name)
                print(addressService.findById(it.addressId).toString())
                println("\n\tCompetencias: " + skillService.findSkillsByJob(it.id).join(", "))
            }
        }
    }

    private void printJobList(List<Job> jobs) {
        jobs.eachWithIndex { job, i -> println "[${i + 1}] ${job}\n"
        }
    }

    private void isValidOption(int option, int size) {
        if (option <= 0 || option > size) {
            println "[AVISO]: Opção inválida!"
        }
    }
}