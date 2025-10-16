package org.acelerazg.repositories.db

import groovy.sql.Sql

class DatabaseConnection {

    static Sql getConnection() {
        String url = 'jdbc:postgresql://localhost:5435/linketinderdb'
        String user = 'postgres'
        String password = 'postgres'
        String driver = 'org.postgresql.Driver'
        return Sql.newInstance(url, user, password, driver)
    }

    static void useConnection(Closure action) throws Exception {
        getConnection().withCloseable { connection ->
            action(connection)
        }
    }

}
