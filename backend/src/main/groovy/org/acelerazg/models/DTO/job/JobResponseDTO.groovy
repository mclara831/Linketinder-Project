package org.acelerazg.models.DTO.job

import org.acelerazg.models.Company

import java.time.LocalDate

class JobResponseDTO {
    String id
    String name
    String description
    String address
    LocalDate publicationDate
    Company company
    String skills

    JobResponseDTO() {}

    @Override
    String toString() {
        return "\nJob: " +
                "\n\tname= " + name +
                "\n\tdescription= " + description +
                "\n\tpublication date= " + publicationDate +
                "\n\taddress=" + address +
                "\n\t" + company +
                "\n\tskills=" + skills
    }
}
