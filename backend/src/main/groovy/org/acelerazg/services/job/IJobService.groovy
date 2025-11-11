package org.acelerazg.services.job


import org.acelerazg.models.DTO.job.JobDTO
import org.acelerazg.models.DTO.job.JobResponseDTO
import org.acelerazg.models.Job

interface IJobService {

    List<JobResponseDTO> findAll()
    List<Job> findAllWithId()
    JobResponseDTO findInfoFromJob(Job job)
    JobResponseDTO create(JobDTO dto)
    JobResponseDTO update(String id, JobDTO dto)
    void deleteById(String id)
    List<JobResponseDTO> findJobFromACompany(String cnpj)
}
