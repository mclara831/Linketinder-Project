package org.acelerazg.models.DTO.candidate

import org.acelerazg.models.Address
import org.acelerazg.models.Candidate

class CandidateConversionResult {
    Candidate candidate
    Address address
    String skills

    CandidateConversionResult(Candidate candidate, Address address, String skills) {
        this.candidate = candidate
        this.address = address
        this.skills = skills
    }
}