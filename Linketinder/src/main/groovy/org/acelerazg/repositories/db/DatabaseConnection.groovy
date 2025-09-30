package org.acelerazg.repositories.db

import groovy.sql.Sql

class DatabaseConnection {

    Sql sql

    private Sql loadProps() {
        String url = 'jdbc:postgresql://localhost:5435/linketinderdb'
        String user = 'postgres'
        String password = 'postgres'
        String driver = 'org.postgresql.Driver'
        return Sql.newInstance(url, user, password, driver)
    }

    Sql getConnection() {
        if (sql == null) {
            sql = loadProps()
            return sql
        }
        return sql
    }

    void closeConnection() {
        if (sql != null) {
            sql.close()
        }
    }
}
