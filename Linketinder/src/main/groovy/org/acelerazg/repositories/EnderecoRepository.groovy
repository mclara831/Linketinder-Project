package org.acelerazg.repositories

import groovy.sql.GroovyRowResult
import org.acelerazg.models.Endereco
import org.acelerazg.repositories.db.DatabaseConnection

class EnderecoRepository {
    DatabaseConnection sql

    EnderecoRepository() {
        sql = new DatabaseConnection()
    }

    List<Endereco> findAll() {
        List<Endereco> enderecos = new ArrayList<>()

        sql.getConnection().eachRow("SELECT * FROM enderecos") { rs ->
            enderecos.add(new Endereco(rs.id, rs.pais, rs.estado, rs.cep))
        }

        sql.closeConnection()
        return enderecos
    }

    void createNewEndereco(Endereco endereco) {
        sql.getConnection()
                .execute("""INSERT INTO enderecos(pais, estado, cep) VALUES (?, ?, ?)""",
                        [endereco.pais, endereco.estado, endereco.cep])
    }

    String findIdFromEndereco(Endereco endereco) {
        GroovyRowResult rs = sql.getConnection()
                .firstRow("""SELECT * FROM enderecos where pais=? and estado=? and cep=?""",
                        [endereco.pais, endereco.estado, endereco.cep])
        if (rs != null) {
            return rs.id
        }
        return null
    }

    String findEnderecoFromId(String id) {
        GroovyRowResult rs = sql.getConnection()
                .firstRow("""SELECT * FROM enderecos where id=?""",
                        [id])
        if (rs != null) {
            return new Endereco(rs.pais, rs.estado, rs.cep)
        }
        return null
    }
}
