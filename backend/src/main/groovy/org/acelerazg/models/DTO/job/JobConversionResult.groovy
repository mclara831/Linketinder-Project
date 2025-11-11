package org.acelerazg.models.DTO.job

import org.acelerazg.models.Address
import org.acelerazg.models.Job

class JobConversionResult {
    Job job
    Address address
    String skills

    JobConversionResult(Job job, Address address, String skills) {
        this.job = job
        this.address = address
        this.skills = skills
    }
}