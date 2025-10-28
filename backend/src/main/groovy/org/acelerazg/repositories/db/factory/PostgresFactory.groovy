package org.acelerazg.repositories.db.factory

import org.acelerazg.repositories.AddressRepository
import org.acelerazg.repositories.CandidateRepository
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.repositories.JobRepository
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.repositories.db.connection.DatabaseConnection

class PostgresFactory extends RepositoryFactory {

    PostgresFactory(DatabaseConnection connection) {
        super(connection)
    }

    @Override
    CandidateRepository createCandidateRepository() {
        return new CandidateRepository(connection)
    }

    @Override
    CompanyRepository createCompanyRepository() {
        return new CompanyRepository(connection)
    }

    @Override
    JobRepository createJobRepository() {
        return new JobRepository(connection)
    }

    @Override
    SkillRepository createSkillRepository() {
        return new SkillRepository(connection)
    }

    @Override
    def createAddressRepository() {
        return new AddressRepository(connection)
    }
}
