package org.acelerazg.repositories

import groovy.sql.GroovyRowResult
import org.acelerazg.models.Company
import org.acelerazg.repositories.db.DatabaseConnection

class EmpresaRepository {

    DatabaseConnection sql

    EmpresaRepository() {
        sql = new DatabaseConnection()
    }

    List<Company> findAll() {
        List<Company> empresas = new ArrayList<>()

        sql.getConnection().eachRow("SELECT * FROM empresas") { rs ->
            empresas.add(new Company(rs.id, rs.nome, rs.email, rs.linkedin, rs.endereco_id, rs.descricao, rs.senha, rs.cnpj))
        }

        return empresas
    }

    Company findEmpresaById(String id) {
        GroovyRowResult rs = sql.getConnection().firstRow("SELECT * FROM empresas WHERE id=?", [id])
        if (rs) {
            return new Company(rs.id, rs.nome, rs.email, rs.linkedin, rs.endereco_id, rs.descricao, rs.senha, rs.cnpj)
        }
        return null
    }

    void createNewEmpresa(Company empresa) {
        sql.getConnection().executeInsert(""" INSERT INTO empresas
                (nome, email, linkedin, cnpj, endereco_id, descricao, senha)
                values (?,?,?,?,?,?,?)""",
                [empresa.nome, empresa.email, empresa.linkedin, empresa.cnpj, empresa.enderecoId, empresa.descricao, empresa.senha])
    }

    void updateEmpresaById(Company empresa) {
        sql.getConnection().executeInsert(""" UPDATE empresas 
                        SET nome=?, email=?, linkedin=?, endereco_id=?, descricao=?, senha=? WHERE id=?""",
                [empresa.nome, empresa.email, empresa.linkedin, empresa.enderecoId, empresa.descricao, empresa.senha, empresa.id])
    }

    Company findByCnpj(String cnpj) {
        GroovyRowResult rs = sql.getConnection().firstRow("SELECT * FROM empresas WHERE cnpj=?", [cnpj])
        if (rs) {
            return new Company(rs.id, rs.nome, rs.email, rs.linkedin, rs.endereco_id, rs.descricao, rs.senha, rs.cnpj)
        }
        return null
    }

    void deleteByCnpj(String cnpj) {
        sql.getConnection().execute("DELETE FROM empresas WHERE cnpj=?", [cnpj])
    }
}
