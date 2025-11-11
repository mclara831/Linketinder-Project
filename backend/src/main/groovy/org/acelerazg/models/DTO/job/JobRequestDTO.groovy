package org.acelerazg.models.DTO.job

import org.acelerazg.models.Address
import org.acelerazg.models.Job
import org.acelerazg.models.Skill

class JobRequestDTO {
    String cnpj
    String name
    String description
    String address
    String skills

    JobRequestDTO() {}

    JobRequestDTO(Job job, Address address, String skills, String cnpj) {
        this.name = job.name
        this.cnpj = cnpj
        this.description = job.description
        this.address = address
        this.skills = skills
    }

    JobRequestDTO(Job job, Address address, String skills) {
        this.name = job.name
        this.description = job.description
        this.address = address
        this.skills = skills
    }

    @Override
    String toString() {
        return "\nJob: " +
                "\n\tname= " + name +
                "\n\tcnpj= " + cnpj +
                "\n\tdescription= " + description +
                "\n\taddress=" + address +
                "\n\tskills=" + skills
    }
}
