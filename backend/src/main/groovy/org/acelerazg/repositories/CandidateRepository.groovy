package org.acelerazg.repositories

import org.acelerazg.models.Candidate
import org.acelerazg.repositories.db.DatabaseConnection

import java.time.LocalDate

class CandidateRepository {

    DatabaseConnection sql

    CandidateRepository() {
        sql = new DatabaseConnection()
    }

    List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>()

        try {
            sql.getConnection().eachRow("SELECT * FROM candidatos") { row ->
                candidates.add(mapRowToCandidate(row))
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to list all candidates!\n${e.getMessage()}")
        } finally {
            sql.getConnection().close()
        }

        return candidates
    }

    Candidate create(Candidate candidate) {
        String query = """
                INSERT INTO candidatos 
                (id, nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
                values (?,?,?,?,?,?,?,?,?,?)
                """
        try {
            sql.useConnection { conn ->
                conn.executeInsert(query, [candidate.id, candidate.name, candidate.lastname, candidate.email, candidate.linkedin,
                                           candidate.cpf, candidate.dateOfBirth, candidate.addressId, candidate.description, candidate.password])
            }
            candidate = findById(candidate.id)
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to insert candidate ${candidate.name}!")
        }
        return candidate
    }

    Candidate updateById(Candidate candidate) {
        String query = """
                        UPDATE candidatos 
                        SET nome=?, sobrenome=?, email=?, linkedin=?, data_nascimento=?, endereco_id=?, descricao=?, senha=? 
                        WHERE id=?
                        """
        try {
            sql.useConnection { conn ->
                conn.executeInsert(query, [candidate.name, candidate.lastname, candidate.email, candidate.linkedin,
                                           candidate.dateOfBirth, candidate.addressId, candidate.description, candidate.password, candidate.id])
            }
            candidate = findById(candidate.id)

        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to update candidate ${candidate.cpf}!")
        }
        return candidate
    }


    void deleteByCpf(String cpf) {
        String query = "DELETE FROM candidatos WHERE cpf=?"
        try {
            sql.useConnection { conn ->
                conn.execute(query, [cpf])
            }

        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to update candidate ${candidate.cpf}!")
        }
    }

    Candidate findById(String id) {
        String query = "SELECT * FROM candidatos WHERE id=?"
        return findOne(query, id)
    }

    Candidate findByCpf(String cpf) {
        String query = "SELECT * FROM candidatos WHERE cpf=?"
        return findOne(query, cpf)
    }

    private Candidate findOne(String query, String param) {
        try {
            sql.useConnection { conn ->
                def result = conn.firstRow(query, param)
                if (result) {
                    return mapRowToCandidate(result)
                }
            }
        } catch (Exception e) {
            println("find candidate with params ${param}")
        }
        return null
    }

    private Candidate mapRowToCandidate(Object rs) {
        LocalDate birthDate = rs.data_nascimento ? rs.data_nascimento.toLocalDate() : null

        return new Candidate(
                rs.id,
                rs.nome,
                rs.sobrenome,
                rs.email,
                rs.linkedin,
                rs.cpf,
                birthDate,
                rs.endereco_id,
                rs.descricao,
                rs.senha
        )
    }
}
