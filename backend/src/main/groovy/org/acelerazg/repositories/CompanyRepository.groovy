package org.acelerazg.repositories

import groovy.sql.GroovyRowResult
import org.acelerazg.models.Company
import org.acelerazg.repositories.db.DatabaseConnection

class CompanyRepository {

    DatabaseConnection sql

    CompanyRepository() {
        sql = new DatabaseConnection()
    }

    List<Company> findAll() {
        List<Company> companies = new ArrayList<>()

        try {
            sql.getConnection().eachRow("SELECT * FROM empresas") { row ->
                companies.add(mapRowToCompany(row))
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to list all companies!\n${e.getMessage()}")
        } finally {
            sql.getConnection().close()
        }
        return companies
    }

    Company create(Company company) {
        String query = """
                INSERT INTO empresas
                (id, nome, email, linkedin, cnpj, endereco_id, descricao, senha)
                values (?,?,?,?,?,?,?,?)"""

        try {
            sql.getConnection().executeInsert(query, [company.id, company.name, company.email, company.linkedin, company.cnpj,
                                                      company.addressId, company.description, company.password])

            company = findById(company.id)
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to insert company ${company.name}!\n${e.getMessage()}")
        } finally {
            sql.getConnection().close()
        }
        return company
    }

    Company updateById(Company company) {
        String query = """UPDATE empresas 
                        SET nome=?, email=?, linkedin=?, endereco_id=?, descricao=?, senha=? 
                        WHERE id=?"""
        try {
            sql.getConnection().executeInsert(query, [company.name, company.email, company.linkedin,
                                                      company.addressId, company.description, company.password, company.id])

            company = findById(company.id)
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to update company ${company.cnpj}!")
        } finally {
            sql.getConnection().close()
        }
        return company
    }

    void deleteByCnpj(String cnpj) {
        String query = "DELETE FROM empresas WHERE cnpj=?"
        try {
            sql.getConnection().execute(query, [cnpj])
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to delete company ${cnpj}!")
        } finally {
            sql.getConnection().close()
        }
    }

    Company findById(String id) {
        String query = "SELECT * FROM empresas WHERE id=?"
        try {
            GroovyRowResult rs = sql.getConnection().firstRow(query, [id])
            if (rs) {
                return mapRowToCompany(rs)
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to delete company ${cnpj}!")
        } finally {
            sql.getConnection().close()
        }
        return null
    }

    Company findByCnpj(String cnpj) {
        try {
            GroovyRowResult rs = sql.getConnection().firstRow("SELECT * FROM empresas WHERE cnpj=?", [cnpj])
            if (rs) {
                return mapRowToCompany(rs)
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to delete company ${cnpj}!")
        } finally {
            sql.getConnection().close()
        }
        return null
    }

    private Company mapRowToCompany(Object rs) {
        return new Company(rs.id, rs.nome, rs.email, rs.linkedin,
                rs.endereco_id, rs.descricao, rs.senha, rs.cnpj)
    }
}
