package org.acelerazg.repositories.db.factory


import org.acelerazg.repositories.CandidateRepository
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.repositories.JobRepository
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.repositories.db.connection.DatabaseConnection

abstract class RepositoryFactory {

    protected DatabaseConnection connection

    RepositoryFactory(DatabaseConnection connection) {
        this.connection = connection
    }

    abstract CandidateRepository createCandidateRepository()
    abstract CompanyRepository createCompanyRepository()
    abstract JobRepository createJobRepository()
    abstract SkillRepository createSkillRepository()
    abstract createAddressRepository()
}
