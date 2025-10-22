package org.acelerazg.controllers

import org.acelerazg.cli.UI
import org.acelerazg.models.Address
import org.acelerazg.models.DTO.JobResponseDTO
import org.acelerazg.models.Job
import org.acelerazg.services.job.JobService

class JobController {

    JobService jobService

    JobController(JobService jobService) {
        this.jobService = jobService
    }

    void findAll() {
        List<JobResponseDTO> jobs = jobService.findAll()
        jobs.forEach { it ->  println(it.toString())}
    }

    void create() {
        String cnpj = UI.requestCnpj()

        Job job = UI.readJobInfo()
        Address address = UI.readAdress()
        String skills = UI.readSkills()

        Job created = jobService.create(job, address, skills, cnpj)
        if(created) println("[CREATED]: ${created.toString()}")
    }

    void update() {
        List<Job> jobs = jobService.findAllWithId()
        printJobList(jobs)

        int option = UI.readInt()
        isValidOption(option, jobs.size())

        Job job = UI.readJobInfo()
        Address address = UI.readAdress()
        String skills = UI.readSkills()

        job.id = jobs[option - 1].id

        Job result = jobService.update(job, address, skills)
        if(result) println("[UPDATED]: ${result.toString()}")
    }

    void delete() {
        List<Job> jobs = jobService.findAllWithId()
        printJobList(jobs)

        int option = UI.readInt()
        isValidOption(option, jobs.size())

        jobService.deleteById(jobs[option - 1].id)
    }

    void findJobFromACompany() {

        String cnpj = UI.requestCnpj()

        List<JobResponseDTO> jobs = jobService.findJobFromACompany(cnpj)
        if (jobs.isEmpty()) {
            println("[AVISO]: Não foram encontradas vagas cadastradas nesse CNPJ!")
            return
        }

        jobs.forEach { it ->  println(it.toString()) }
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