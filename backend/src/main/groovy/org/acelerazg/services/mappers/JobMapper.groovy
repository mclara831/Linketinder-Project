package org.acelerazg.services.mappers

import org.acelerazg.models.Company
import org.acelerazg.models.DTO.job.JobResponseDTO
import org.acelerazg.models.Job

class JobMapper {
    JobResponseDTO mapToDto(Job job, Company company, String address, String skills) {
        JobResponseDTO jobResponseDTO = new JobResponseDTO()
        jobResponseDTO.id = job.id
        jobResponseDTO.name = job.name
        jobResponseDTO.description = job.description
        jobResponseDTO.publicationDate = job.createdAt
        jobResponseDTO.company = company
        jobResponseDTO.address = address
        jobResponseDTO.skills = skills
        return jobResponseDTO
    }
}
