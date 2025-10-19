package org.acelerazg.repositories

import org.acelerazg.models.Candidate
import org.acelerazg.repositories.db.BaseRepository

import java.time.LocalDate

class CandidateRepository extends BaseRepository<Candidate> {

    CandidateRepository() {}

    List<Candidate> findAll() {
        String query = "SELECT * FROM candidatos"
        return findAllRows(query)
    }

    Candidate findById(String id) {
        String query = "SELECT * FROM candidatos WHERE id=?"
        return findOne(query, [id])
    }

    Candidate findByCpf(String cpf) {
        String query = "SELECT * FROM candidatos WHERE cpf=?"
        return findOne(query, [cpf])
    }

    Candidate create(Candidate candidate) {
        String query = """
                INSERT INTO candidatos
                (id, nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
                values (?,?,?,?,?,?,?,?,?,?)
                """

        executeUpdate(query, [candidate.id, candidate.name, candidate.lastname, candidate.email, candidate.linkedin,
                              candidate.cpf, candidate.dateOfBirth, candidate.addressId, candidate.description, candidate.password])
        return findById(candidate.id)
    }

    Candidate updateById(Candidate candidate) {
        String query = """
                        UPDATE candidatos
                        SET nome=?, sobrenome=?, email=?, linkedin=?, data_nascimento=?, endereco_id=?, descricao=?, senha=?
                        WHERE id=?
                        """
        executeUpdate(query, [candidate.name, candidate.lastname, candidate.email, candidate.linkedin,
                              candidate.dateOfBirth, candidate.addressId, candidate.description, candidate.password, candidate.id])
        return findById(candidate.id)
    }

    void deleteByCpf(String cpf) {
        String query = "DELETE FROM candidatos WHERE cpf=?"
        executeDelete(query, [cpf])
    }

    @Override
    protected String getTableName() {
        return "candidates"
    }

    protected Candidate mapRowToEntity(Object rs) {
        LocalDate birthDate = rs.data_nascimento ? rs.data_nascimento.toLocalDate() : null

        return new Candidate(rs.id,
                rs.nome,
                rs.sobrenome,
                rs.email,
                rs.linkedin,
                rs.cpf,
                birthDate,
                rs.endereco_id,
                rs.descricao,
                rs.senha)
    }
}
