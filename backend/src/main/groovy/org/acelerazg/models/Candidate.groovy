package org.acelerazg.models

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class Candidate extends Person {

    String lastname
    LocalDate dateOfBirth
    String cpf

    Candidate(String name, String lastname, String email, String linkedin, String cpf, LocalDate dateOfBirth, String description, String password) {
        super(name, email, linkedin, description, password)
        this.lastname = lastname
        this.dateOfBirth = dateOfBirth
        this.cpf = cpf
    }

    Candidate(String name, String lastname, String email, String linkedin, String cpf, LocalDate dateOfBirth, String addressId, String description, String password) {
        super(name, email, linkedin, addressId, description, password)
        this.lastname = lastname
        this.dateOfBirth = dateOfBirth
        this.cpf = cpf
    }

    Candidate(String id, String name, String lastname, String email, String linkedin, String cpf, LocalDate dateOfBirth, String addressId, String description, String password) {
        super(id, name, email, linkedin, addressId, description, password)
        this.lastname = lastname
        this.dateOfBirth = dateOfBirth
        this.cpf = cpf
    }

    @Override
    String toString() {
        return "\n\nCandidate:\n" +
                "\tname = " + name +
                " " + lastname +
                "\n\temail = " + email +
                "\n\tcpf = " + cpf +
                "\n\tdate of birth = " + dateOfBirth +
                "\n\tlinkedin = " + linkedin +
                "\n\tdescription = " + description
    }
}