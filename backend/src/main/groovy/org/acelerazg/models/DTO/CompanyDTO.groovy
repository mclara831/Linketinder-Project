package org.acelerazg.models.DTO

import org.acelerazg.models.Address
import org.acelerazg.models.Company

class CompanyDTO {
    String name
    String cnpj
    String email
    String linkedin
    String description
    String address
    String skills
    String password

    CompanyDTO() {}

    CompanyDTO(Company company, Address address, String skills) {
        this.name = company.name
        this.cnpj = company.cnpj
        this.email = company.email
        this.linkedin = company.linkedin
        this.description = company.description
        this.password = company.password
        this.address = address
        this.skills = skills
    }

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
