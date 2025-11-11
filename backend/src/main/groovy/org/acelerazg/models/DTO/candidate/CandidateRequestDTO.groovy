package org.acelerazg.models.DTO.candidate

import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.models.Skill

import java.time.LocalDate

class CandidateRequestDTO {
    String name
    String lastname
    String cpf
    String email
    String linkedin
    String description
    LocalDate dateOfBirth
    String address
    String skills
    String password

    CandidateRequestDTO() {}

    CandidateRequestDTO(Candidate candidate, Address address, String skills) {
        this.name = candidate.name
        this.lastname = candidate.lastname
        this.cpf = candidate.cpf
        this.email = candidate.email
        this.linkedin = candidate.linkedin
        this.description = candidate.description
        this.dateOfBirth = candidate.dateOfBirth
        this.password = candidate.password
        this.address = address.toString()
        this.skills = skills
    }

    @Override
    String toString() {
        return "\nCandidate: " +
                "\n\tname= " + name + " " + lastname +
                "\n\tcpf= " + cpf +
                "\n\temail= " + email +
                "\n\tlinkedin= " + linkedin +
                "\n\tdescription= " + description +
                "\n\tdateOfBirth= " + dateOfBirth +
                "\n\taddress=" + address +
                "\n\tskills=" + skills +
                "\n\tpassword= " + password
    }
}
