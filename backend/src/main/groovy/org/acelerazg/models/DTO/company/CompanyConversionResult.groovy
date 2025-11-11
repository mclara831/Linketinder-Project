package org.acelerazg.models.DTO.company

import org.acelerazg.models.Address
import org.acelerazg.models.Company

class CompanyConversionResult {
    Company company
    Address address
    String skills

    CompanyConversionResult(Company company, Address address, String skills) {
        this.company = company
        this.address = address
        this.skills = skills
    }
}