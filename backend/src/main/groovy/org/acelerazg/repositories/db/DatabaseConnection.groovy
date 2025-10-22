package org.acelerazg.repositories.db

import groovy.sql.Sql

interface DatabaseConnection {

    Sql getConnection();
}
