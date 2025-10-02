package org.acelerazg.repositories

import groovy.sql.GroovyRowResult
import org.acelerazg.models.Candidato
import org.acelerazg.models.Vaga
import org.acelerazg.repositories.db.DatabaseConnection

import java.time.LocalDate

class VagaRepository {

    DatabaseConnection sql

    VagaRepository() {
        sql = new DatabaseConnection()
    }

    List<Vaga> findAll() {
        List<Vaga> vagas = new ArrayList<>()

        sql.getConnection().eachRow("SELECT * FROM vagas") { rs ->
            LocalDate criadaEm = rs.criada_em?.toLocalDate()
            vagas.add(new Vaga(rs.id, rs.nome, rs.descricao, criadaEm, rs.endereco_id, rs.empresa_id))
        }
        return vagas
    }

    void createNewVaga(Vaga vaga) {
        sql.getConnection().executeInsert(""" INSERT INTO vagas 
                (id, nome, descricao, endereco_id, empresa_id)
                values (?,?,?,?,?)""",
                [vaga.id, vaga.nome, vaga.descricao, vaga.enderecoId, vaga.empresaId])
    }

    void updateVagaById(Vaga vaga) {
        sql.getConnection().executeInsert(""" UPDATE vagas 
                        SET nome=?, descricao=?, endereco_id=? where id=?""",
                [vaga.nome, vaga.descricao, vaga.enderecoId, vaga.id])
    }

    Vaga findVagaById(String id) {
        GroovyRowResult rs = sql.getConnection().firstRow("SELECT * FROM vagas WHERE id=?", [id])
        if (rs) {
            LocalDate criadaEm = rs.criada_em?.toLocalDate()
            return new Vaga(rs.id, rs.nome, rs.descricao, criadaEm, rs.endereco_id, rs.empresa_id)
        }
        return null
    }

    void deleteById(String id) {
        sql.getConnection().execute("DELETE FROM vagas WHERE id=?", [id])
    }

    List<Vaga> findVagasByEmpresaId(String empresaId) {
        List<Vaga> vagas = new ArrayList<>()
        sql.getConnection().eachRow("SELECT * FROM vagas WHERE empresa_id=?", [empresaId]) { rs ->
            LocalDate criadaEm = rs.criada_em?.toLocalDate()
            vagas.add(new Vaga(rs.id, rs.nome, rs.descricao, criadaEm, rs.endereco_id, rs.empresa_id))
        }
        return vagas
    }
}
