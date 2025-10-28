package org.acelerazg.repositories

import org.acelerazg.models.Company
import org.acelerazg.repositories.db.BaseRepository
import org.acelerazg.repositories.db.connection.DatabaseConnection

class CompanyRepository extends BaseRepository<Company> {

    CompanyRepository(DatabaseConnection sql) {
        super(sql)
    }

    List<Company> findAll() {
        String query = "SELECT * FROM empresas"
        return findAllRows(query)
    }

    Company findById(String id) {
        String query = "SELECT * FROM empresas WHERE id=?"
        return findOne(query, [id])
    }

    Company findByCnpj(String cnpj) {
        String query = "SELECT * FROM empresas WHERE cnpj=?"
        return findOne(query, [cnpj])
    }

    Company create(Company company) {
        String query = """
                INSERT INTO empresas
                (id, nome, email, linkedin, cnpj, endereco_id, descricao, senha)
                values (?,?,?,?,?,?,?,?)"""
        executeUpdate(query, [company.id, company.name, company.email, company.linkedin, company.cnpj,
                              company.addressId, company.description, company.password])

        return findById(company.id)
    }

    Company updateById(Company company) {
        String query = """UPDATE empresas 
                        SET nome=?, email=?, linkedin=?, endereco_id=?, descricao=?, senha=? 
                        WHERE id=?"""
        executeUpdate(query, [company.name, company.email, company.linkedin,
                              company.addressId, company.description, company.password, company.id])

        return findById(company.id)
    }

    void deleteByCnpj(String cnpj) {
        String query = "DELETE FROM empresas WHERE cnpj=?"
        executeDelete(query, [cnpj])
    }

    @Override
    protected String getTableName() {
        return "empresas"
    }

    @Override
    protected Company mapRowToEntity(Object row) {
        return new Company(row.id, row.nome, row.email, row.linkedin,
                row.endereco_id, row.descricao, row.senha, row.cnpj)
    }
}
