package org.acelerazg.models.DTO

class CompanyResponseDTO {
    String name
    String cnpj
    String email
    String linkedin
    String description
    String address
    String skills
    String password

    CompanyResponseDTO() {}

    @Override
    String toString() {
        return "\nCandidate: " +
                "\n\tname= " + name +
                "\n\tcnpj= " + cnpj +
                "\n\temail= " + email +
                "\n\tlinkedin= " + linkedin +
                "\n\tdescription= " + description +
                "\n\taddress=" + address +
                "\n\tskills=" + skills +
                "\n\tpassword= " + password
    }
}
