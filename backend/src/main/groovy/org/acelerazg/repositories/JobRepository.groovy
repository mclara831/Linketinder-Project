package org.acelerazg.repositories

import groovy.sql.GroovyRowResult
import org.acelerazg.models.Job
import org.acelerazg.repositories.db.DatabaseConnection

import java.time.LocalDate

class JobRepository {

    DatabaseConnection sql

    JobRepository() {
        sql = new DatabaseConnection()
    }

    List<Job> findAll() {
        List<Job> vagas = new ArrayList<>()

        try {
            sql.getConnection().eachRow("SELECT * FROM vagas") { rs ->
                LocalDate criadaEm = rs.criada_em?.toLocalDate()
                vagas.add(new Job(rs.id, rs.nome, rs.descricao, criadaEm, rs.endereco_id, rs.empresa_id))
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to list all jobs")
        } finally {
            sql.getConnection().close()
        }
        return vagas
    }

    Job create(Job job) {
        String query = """ 
                        INSERT INTO vagas 
                        (id, nome, descricao, endereco_id, empresa_id)
                         values (?,?,?,?,?)
                         """
        Job result = null
        try {
            sql.getConnection().executeInsert(query, [job.id, job.name, job.description, job.addressId, job.companyId])
            result = findById(job.id)
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to insert a new job.")
        } finally {
            sql.getConnection().close()
        }
        return result
    }

    Job update(Job job) {
        String query = "UPDATE vagas SET nome=?, descricao=?, endereco_id=? where id=?"
        Job result = null
        try {
            sql.getConnection().executeInsert(query, [job.name, job.description, job.addressId, job.id])
            result = findById(job.id)
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to update the job.")
        } finally {
            sql.getConnection().close()
        }
        return result
    }

    Job findById(String id) {
        try {
            GroovyRowResult rs = sql.getConnection().firstRow("SELECT * FROM vagas WHERE id=?", [id])
            if (rs) {
                return mapperRowToJob(rs)
            }
        }catch (Exception e) {
            println("[ERROR]: It wasn't possible to find the job.")
        } finally {
            sql.getConnection().close()
        }
        return null
    }

    void delete(String id) {
        try {
            sql.getConnection().execute("DELETE FROM vagas WHERE id=?", [id])
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to delete the job.")
        } finally {
            sql.getConnection().close()
        }
    }

    List<Job> findJobFromACompany(String empresaId) {
        List<Job> vagas = new ArrayList<>()
        try {
            sql.getConnection().eachRow("SELECT * FROM vagas WHERE empresa_id=?", [empresaId]) { rs ->
                vagas.add(mapperRowToJob(rs))
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to find jobs from company.")
        } finally {
            sql.getConnection().close()
        }
        return vagas
    }

    Job mapperRowToJob(Object rs) {
        LocalDate createdAt = rs.criada_em?.toLocalDate()
        return new Job(rs.id, rs.nome, rs.descricao, createdAt, rs.endereco_id, rs.empresa_id)
    }
}
