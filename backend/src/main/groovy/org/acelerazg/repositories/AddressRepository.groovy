package org.acelerazg.repositories

import org.acelerazg.models.Address
import org.acelerazg.repositories.db.BaseRepository
import org.acelerazg.repositories.db.connection.DatabaseConnection

class AddressRepository extends BaseRepository<Address> {

    AddressRepository(DatabaseConnection sql) {
        super(sql)
    }

    String findById(String id) {
        String query = "SELECT * FROM enderecos where id=?"
        return findOne(query, [id])
    }

    String findByAddress(Address address) {
        String query = "SELECT * FROM enderecos where pais=? and estado=? and cep=?"
        Address result = findOne(query, [ address.country, address.region, address.cep])
        if (result)
            return result.id
        else
            return null
    }

    void create(Address address) {
        String query = "INSERT INTO enderecos(id, pais, estado, cep) VALUES (?,?,?,?)"
        executeUpdate(query, [address.id, address.country, address.region, address.cep])
    }

    @Override
    protected String getTableName() {
        return "enderecos"
    }

    @Override
    protected Address mapRowToEntity(Object row) {
        return new Address(row.pais, row.estado, row.cep)
    }
}
