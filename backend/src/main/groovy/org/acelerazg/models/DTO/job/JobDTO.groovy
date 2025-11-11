package org.acelerazg.models.DTO.job

import org.acelerazg.models.Address
import org.acelerazg.models.Job

class JobDTO {
    String cnpj
    String name
    String description
    String address
    String skills

    JobDTO() {}

    JobDTO( Job job, Address address, String skills, String cnpj) {
        this.name = job.name
        this.cnpj = cnpj
        this.description = job.description
        this.address = address
        this.skills = skills
    }

    JobDTO( Job job, Address address, String skills) {
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
