package org.acelerazg.models

import java.time.LocalDate

class Job {
    String id
    String name
    String description
    LocalDate createdAt
    String addressId
    String companyId

    Job(String id, String name, String description, LocalDate createdAt, String addressId, String companyId) {
        this.id = id
        this.name = name
        this.description = description
        this.createdAt = createdAt
        this.addressId = addressId
        this.companyId = companyId
    }

    Job(String name, String description) {
        this.name = name
        this.description = description
    }

    Job(String id, String name, String description) {
        this.id = id
        this.name = name
        this.description = description
    }

    @Override
    String toString() {
        return "Vaga: " +
                "\n\tnome=" + name +
                "\n\tdescricao=" + description +
                "\n\tcriada em=" + createdAt
    }
}
