package org.acelerazg.models.DTO

import java.time.LocalDate

class CandidateResponseDTO {
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

    CandidateResponseDTO() {}

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
