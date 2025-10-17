package org.acelerazg.services

import org.acelerazg.models.Company
import org.acelerazg.models.Endereco
import org.acelerazg.models.Job
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.repositories.JobRepository
import org.acelerazg.utils.Utils

class JobService {

    JobRepository jobRepository
    EnderecoService addressService
    SkillService skillService
    CompanyRepository companyRepository

    JobService() {
        this(new JobRepository(), new EnderecoService(), new SkillService(), new CompanyRepository())
    }

    JobService(JobRepository jobRepository, EnderecoService addressService, SkillService skillService, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository
        this.addressService = addressService
        this.skillService = skillService
        this.companyRepository = companyRepository
    }

    List<Job> findAll() {
        return jobRepository.findAll()
    }

    Job create(Job job, Endereco address, String skills, String cnpj) {
        Company company = companyRepository.findByCnpj(cnpj)

        job.id = Utils.generateUUID()
        job.addressId = addressService.find(address)
        job.companyId = company.id

        Job created = jobRepository.create(job)
        skillService.addSkillsToJob(job.id, skills)
        return created
    }

    Job update(Job job, Endereco address, String skills) {
        String addressId = addressService.find(address)
        job.addressId = addressId

        skillService.removeSkillsFromJob(job.id)
        skillService.addSkillsToJob(job.id, skills)

        return jobRepository.update(job)
    }

    void deleteById(String id) {
        skillService.removeSkillsFromJob(id)
        jobRepository.delete(id)
    }

    List<Job> findJobFromACompany(String cnpj) {
        Company company = companyRepository.findByCnpj(cnpj)
        return jobRepository.findJobFromACompany(company.id)
    }
}
