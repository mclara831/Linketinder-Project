package org.acelerazg.repositories

import groovy.sql.GroovyRowResult
import org.acelerazg.models.Address
import org.acelerazg.repositories.db.DatabaseConnection

class AddressRepository {
    DatabaseConnection sql

    AddressRepository() {
        sql = new DatabaseConnection()
    }

    void create(Address address) {
        String query = "NSERT INTO enderecos(id, pais, estado, cep) VALUES (?,?,?,?)"
        try {
            sql.getConnection()
                    .execute(query,
                            [address.id, address.country, address.region, address.cep])
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to insert address.")
        } finally {
            sql.getConnection().close()
        }

    }

    String findByAddress(Address address) {
        String query = "SELECT * FROM enderecos where pais=? and estado=? and cep=?"
        try {
            GroovyRowResult rs = sql.getConnection().firstRow(query, [address.country, address.region, address.cep])
            if (rs != null) {
                return rs.id
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to find address.")
        } finally {
            sql.getConnection().close()
        }
        return null
    }

    String findEnderecoFromId(String id) {
        String query = "SELECT * FROM enderecos where id=?"
        try {
            GroovyRowResult rs = sql.getConnection().firstRow(query, [id])
            if (rs != null) {
                return new Address(rs.pais, rs.estado, rs.cep)
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to find address.")
        } finally {
            sql.getConnection().close()
        }
        return null
    }
}
