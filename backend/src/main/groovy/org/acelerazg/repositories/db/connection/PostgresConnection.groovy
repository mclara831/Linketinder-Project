package org.acelerazg.repositories.db.connection

import groovy.sql.Sql

class PostgresConnection implements DatabaseConnection {

    private static PostgresConnection instance
    private Sql connection

    PostgresConnection() {
        this.connection = createConnection()
    }

    private static Sql createConnection() {
        String url = 'jdbc:postgresql://localhost:5435/linketinderdb'
        String user = 'postgres'
        String password = 'postgres'
        String driver = 'org.postgresql.Driver'
        return Sql.newInstance(url, user, password, driver)
    }

    static synchronized PostgresConnection getInstance() {
        if (instance == null) {
            instance = new PostgresConnection()
        }
        return instance
    }

    @Override
    Sql getConnection() {
        if (connection == null || connection.connection.isClosed())
            connection = createConnection()
        return connection
    }
}
