package org.acelerazg.models

class Company extends Person {

    String cnpj

    Company(String id, String nome, String email, String linkedin, String adressId, String description, String password, String cnpj) {
        super(id, nome, email, linkedin, adressId, description, password)
        this.cnpj = cnpj
    }

    Company(String nome, String email, String linkedin, String adressId, String description, String password, String cnpj) {
        super(nome, email, linkedin, adressId, description, password)
        this.cnpj = cnpj
    }

    @Override
    String toString() {
        return "\nEmpresa: " + name +
                "\n\temail = " + email +
                "\n\tlinkedin = " + linkedin +
                "\n\tcnpj = " + cnpj +
                "\n\tdescription = " + description
    }
}
