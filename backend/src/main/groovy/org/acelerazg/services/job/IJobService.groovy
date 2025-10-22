package org.acelerazg.services.job

import org.acelerazg.models.Address
import org.acelerazg.models.DTO.JobResponseDTO
import org.acelerazg.models.Job

interface IJobService {

    List<JobResponseDTO> findAll()
    List<Job> findAllWithId()
    JobResponseDTO findInfoFromJob(Job job)
    Job create(Job job, Address address, String skills, String cnpj)
    Job update(Job job, Address address, String skills)
    void deleteById(String id)
    List<JobResponseDTO> findJobFromACompany(String cnpj)
}
