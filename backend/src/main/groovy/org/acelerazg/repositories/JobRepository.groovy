package org.acelerazg.repositories

import org.acelerazg.models.Job
import org.acelerazg.repositories.db.BaseRepository
import org.acelerazg.repositories.db.connection.DatabaseConnection

import java.time.LocalDate

class JobRepository extends BaseRepository<Job> {

    JobRepository(DatabaseConnection sql) {
        super(sql)
    }

    List<Job> findAll() {
        String query = "SELECT * FROM vagas"
        return findAllRows(query)
    }

    Job findById(String id) {
        String query = "SELECT * FROM vagas WHERE id=?"
        return findOne(query, [id])
    }

    List<Job> findJobFromCompany(String empresaId) {
        String query = "SELECT * FROM vagas WHERE empresa_id=?"
        return findAllRows(query, [empresaId])
    }

    Job create(Job job) {
        String query = """ 
                        INSERT INTO vagas 
                        (id, nome, descricao, endereco_id, empresa_id)
                         values (?,?,?,?,?)
                         """
        executeUpdate(query, [job.id, job.name, job.description, job.addressId, job.companyId])
        return findById(job.id)
    }

    Job update(Job job) {
        String query = "UPDATE vagas SET nome=?, descricao=?, endereco_id=? where id=?"
        executeUpdate(query, [job.name, job.description, job.addressId, job.id])
        return findById(job.id)
    }

    void delete(String id) {
        String query = "DELETE FROM vagas WHERE id=?"
        executeDelete(query, [id])
    }

    @Override
    protected String getTableName() {
        return "vagas"
    }

    @Override
    protected Job mapRowToEntity(Object row) {
        LocalDate createdAt = row.criada_em?.toLocalDate()
        return new Job(row.id, row.nome, row.descricao, createdAt, row.endereco_id, row.empresa_id)
    }
}
