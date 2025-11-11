package org.acelerazg.views

import org.acelerazg.cli.UI
import org.acelerazg.models.Address
import org.acelerazg.models.DTO.job.JobDTO
import org.acelerazg.models.DTO.job.JobResponseDTO
import org.acelerazg.models.Job
import org.acelerazg.services.job.JobService

class JobView {

    JobService jobService

    JobView(JobService jobService) {
        this.jobService = jobService
    }

    void findAll() {
        List<JobResponseDTO> jobs = jobService.findAll()
        jobs.forEach { it -> println(it.toString()) }
    }

    void create() {
        try {
            String cnpj = UI.requestCnpj()

            Job job = UI.readJobInfo()
            Address address = UI.readAdress()
            String skills = UI.readSkills()

            JobResponseDTO created = jobService.create(new JobDTO(job, address, skills, cnpj))
            if (created) println("[CREATED]: ${created.toString()}")

        } catch (Exception e) {
            println(e.getMessage())
        }
    }

    void update() {
        try {
            List<Job> jobs = jobService.findAllWithId()
            printJobList(jobs)

            int option = UI.readInt()
            isValidOption(option, jobs.size())

            Job job = UI.readJobInfo()
            Address address = UI.readAdress()
            String skills = UI.readSkills()

            String id = jobs[option - 1].id

            JobDTO dto = new JobDTO(job, address, skills)

            JobResponseDTO result = jobService.update(id, dto)
            if (result) println("[UPDATED]: ${result.toString()}")
        } catch (Exception e) {
            println(e.getMessage())
        }
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

        jobs.forEach { it -> println(it.toString()) }
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