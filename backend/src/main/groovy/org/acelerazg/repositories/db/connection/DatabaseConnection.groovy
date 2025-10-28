package org.acelerazg.repositories.db.connection

import groovy.sql.Sql

interface DatabaseConnection {

    Sql getConnection();
}
