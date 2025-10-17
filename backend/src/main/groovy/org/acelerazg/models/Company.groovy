package org.acelerazg.models


class Company extends Person {

    String cnpj

    Company(String id, String name, String email, String linkedin, String addressId, String description, String password, String cnpj) {
        super(id, name, email, linkedin, addressId, description, password)
        this.cnpj = cnpj
    }

    Company(String name, String email, String linkedin, String description, String password, String cnpj) {
        super(name, email, linkedin, description, password)
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
