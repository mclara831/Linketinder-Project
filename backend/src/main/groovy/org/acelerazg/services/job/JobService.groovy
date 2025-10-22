package org.acelerazg.services.job

import org.acelerazg.models.Company
import org.acelerazg.models.Address
import org.acelerazg.models.DTO.JobResponseDTO
import org.acelerazg.models.Job
import org.acelerazg.repositories.JobRepository
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.company.ICompanyService
import org.acelerazg.services.mappers.JobMapper
import org.acelerazg.services.skill.JobSkillService
import org.acelerazg.utils.Utils

class JobService implements IJobService {

    JobRepository jobRepository
    IAddressService addressService
    JobSkillService jobSkillService
    ICompanyService companyService
    JobMapper jobMapper

    JobService(JobRepository jobRepository, IAddressService addressService, JobSkillService jobSkillService, ICompanyService companyService, JobMapper jobMapper) {
        this.jobRepository = jobRepository
        this.addressService = addressService
        this.jobSkillService = jobSkillService
        this.companyService = companyService
        this.jobMapper = jobMapper
    }

    @Override
    List<JobResponseDTO> findAll() {
        List<Job> jobs =  jobRepository.findAll()
        return jobs.collect{findInfoFromJob(it) }
    }

    @Override
    List<Job> findAllWithId() {
        return jobRepository.findAll()
    }

    @Override
    JobResponseDTO findInfoFromJob(Job job) {
        String address = addressService.findById(job.addressId).toString()
        String skills = jobSkillService.findSkills(job.id) ?: []
        if (skills) skills.join(", ")
        Company company = companyService.findById(job.companyId)
        return jobMapper.mapToDto(job, company, address, skills)
    }

    @Override
    Job create(Job job, Address address, String skills, String cnpj) {
        Company company = companyService.findByCnpj(cnpj)
        if (company == null) {
            println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
            return null
        }

        job.id = Utils.generateUUID()
        job.addressId = addressService.find(address)
        job.companyId = company.id

        Job created = jobRepository.create(job)
        jobSkillService.addSkillsToEntity(job.id, skills)
        return created
    }

    @Override
    Job update(Job job, Address address, String skills) {
        String addressId = addressService.find(address)
        job.addressId = addressId

        jobSkillService.removeSkillsFromEntity(job.id)
        jobSkillService.addSkillsToEntity(job.id, skills)

        return jobRepository.update(job)
    }

    @Override
    void deleteById(String id) {
        jobSkillService.removeSkillsFromEntity(id)
        jobRepository.delete(id)
    }

    @Override
    List<JobResponseDTO> findJobFromACompany(String cnpj) {
        if (!companyService.findByCnpj(cnpj)) {
            println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
            return
        }
        Company company = companyService.findByCnpj(cnpj)
        List<Job> jobs =  jobRepository.findJobFromCompany(company.id)
        return jobs.collect{findInfoFromJob(it)}
    }
}
