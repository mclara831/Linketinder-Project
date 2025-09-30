package org.acelerazg.repositories.db

import org.acelerazg.models.Candidato

class CandidatoRepository {

    DatabaseConnection sql

    CandidatoRepository() {
        sql = new DatabaseConnection()
    }

    List<Candidato> findAll() {
        try {
            def rows = sql.getConnection().rows("SELECT * FROM candidatos")
            println rows.join('\n')
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            sql.closeConnection()
        }
    }
}
