package org.acelerazg.repositories

import groovy.sql.GroovyRowResult
import org.acelerazg.models.Candidato
import org.acelerazg.repositories.db.DatabaseConnection

import java.time.LocalDate

class CandidatoRepository {

    DatabaseConnection sql

    CandidatoRepository() {
        sql = new DatabaseConnection()
    }

    List<Candidato> findAll() {
        List<Candidato> candidatos = new ArrayList<>()

        sql.getConnection().eachRow("SELECT * FROM candidatos") { rs ->
            LocalDate dataNascimento = rs.data_nascimento?.toLocalDate()
            candidatos.add(new Candidato(rs.id, rs.nome, rs.sobrenome, rs.email, rs.linkedin, rs.cpf, dataNascimento, rs.endereco_id, rs.descricao, rs.senha))
        }

        return candidatos
    }

    void createNewCandidato(Candidato candidato) {
        sql.getConnection().executeInsert(""" INSERT INTO candidatos 
                (nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
                values (?,?,?,?,?,?,?,?,?)""",
                [candidato.nome, candidato.sobrenome, candidato.email, candidato.linkedin, candidato.cpf, candidato.dataNascimento, candidato.enderecoId, candidato.descricao, candidato.senha])
    }

    void updateCandidatoById(Candidato candidato) {
        sql.getConnection().executeInsert(""" UPDATE candidatos 
                        SET nome=?, sobrenome=?, email=?, linkedin=?, data_nascimento=?, endereco_id=?, descricao=?, senha=? WHERE id=?""",
                        [candidato.nome, candidato.sobrenome, candidato.email, candidato.linkedin, candidato.dataNascimento, candidato.enderecoId, candidato.descricao, candidato.senha, candidato.id])
    }

    Candidato findByCpf(String cpf) {
        GroovyRowResult rs = sql.getConnection().firstRow("SELECT * FROM candidatos WHERE cpf=?", [cpf])
        if (rs) {
            LocalDate dataNascimento = rs.data_nascimento.toLocalDate()
            return new Candidato(rs.id, rs.nome, rs.sobrenome, rs.email, rs.linkedin, rs.cpf, dataNascimento, rs.endereco_id, rs.descricao, rs.senha)
        }
        return null
    }

    void deleteByCpf(String cpf) {
        sql.getConnection().execute("DELETE FROM candidatos WHERE cpf=?", [cpf])
    }
}
