package org.acelerazg.repositories.db.factory

import org.acelerazg.repositories.AddressRepository
import org.acelerazg.repositories.CandidateRepository
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.repositories.JobRepository
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.repositories.db.connection.DatabaseConnection

class RepositoryFactory{

    private DatabaseConnection connection

    RepositoryFactory(DatabaseConnection connection) {
        this.connection = connection
    }

    CandidateRepository createCandidateRepository() {
        return new CandidateRepository(connection)
    }

    CompanyRepository createCompanyRepository() {
        return new CompanyRepository(connection)
    }

    JobRepository createJobRepository() {
        return new JobRepository(connection)
    }

    SkillRepository createSkillRepository() {
        return new SkillRepository(connection)
    }

    def createAddressRepository() {
        return new AddressRepository(connection)
    }
}
