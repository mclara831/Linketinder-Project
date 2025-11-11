package org.acelerazg.services.job

import org.acelerazg.models.Company
import org.acelerazg.models.Address
import org.acelerazg.models.DTO.job.JobConversionResult
import org.acelerazg.models.DTO.job.JobRequestDTO
import org.acelerazg.models.DTO.job.JobResponseDTO
import org.acelerazg.models.Job
import org.acelerazg.models.Skill
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
        List<Skill> skills = jobSkillService.findSkills(job.id) ?: []
        Company company = companyService.findById(job.companyId)
        return jobMapper.mapToDto(job, company, address, skills)
    }

    @Override
    JobResponseDTO create(JobRequestDTO dto) {
        Company company = new Company()

        try {
            company = companyService.findByCnpj(dto.cnpj)
        }
        catch (Exception e) {
            println(e.getMessage())
            return null
        }

        JobConversionResult result = parseToObject(dto)
        Job job = result.job

        job.id = Utils.generateUUID()
        job.addressId = addressService.find(result.address)
        job.companyId = company.id

        Job created = jobRepository.create(job)
        jobSkillService.addSkillsToEntity(job.id, result.skills)
        return findInfoFromJob(created)
    }

    @Override
    JobResponseDTO update(String id, JobRequestDTO dto) {

        JobConversionResult result = parseToObject(dto)
        Job job = result.job

        String addressId = addressService.find(result.address)
        job.id = id
        job.addressId = addressId

        jobSkillService.removeSkillsFromEntity(job.id)
        jobSkillService.addSkillsToEntity(job.id, dto.skills)

        Job updated = jobRepository.update(job)
        return findInfoFromJob(updated)
    }

    @Override
    void deleteById(String id) {
        jobSkillService.removeSkillsFromEntity(id)
        jobRepository.delete(id)
    }

    @Override
    List<JobResponseDTO> findJobFromACompany(String cnpj) {
        try {
            companyService.findByCnpj(cnpj)
        }
        catch (Exception e) {
            println(e.getMessage())
            return null
        }
        Company company = companyService.findByCnpj(cnpj)
        List<Job> jobs =  jobRepository.findJobFromCompany(company.id)
        return jobs.collect{findInfoFromJob(it)}
    }

    JobConversionResult parseToObject(JobRequestDTO dto) {
        Job job = new Job(dto.name, dto.description)
        Address address = addressService.formatAddress(dto.address)
        return new JobConversionResult(job, address, dto.skills)
    }
}
