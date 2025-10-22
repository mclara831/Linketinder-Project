package org.acelerazg.repositories.db

abstract class BaseRepository<T> {

    protected DatabaseConnection sql

    protected abstract String getTableName()
    protected abstract T mapRowToEntity(def row)

    BaseRepository(DatabaseConnection sql) {
        this.sql = sql
    }

    protected void executeUpdate(String query, List params) {
        def connection = sql.getConnection()
        try {
            connection.executeInsert(query, params)
        } catch (Exception e) {
            logError("Failed to execute update: $query", e)
        } finally {
            connection.close()
        }
    }

    protected T findOne(String query, List params) {
        def connection = sql.getConnection()
        try {
            def row = connection.firstRow(query, params)
            return row ? mapRowToEntity(row) : null
        } catch (Exception e) {
            logError("Failed to execute query: $query", e)
            return null
        } finally {
            connection.close()
        }
    }

    protected List<T> findAllRows(String query, List params = []) {
        def connection = sql.getConnection()
        def results = []
        try {
            connection.eachRow(query, params) { row ->
                results.add(mapRowToEntity(row))
            }
        } catch (Exception e) {
            logError("Failed to execute findAll for ${getTableName()}", e)
        } finally {
            connection.close()
        }
        return results
    }

    protected void executeDelete(String query, List params) {
        def connection = sql.getConnection()
        try {
            connection.execute(query, params)
        } catch (Exception e) {
            logError("Failed to execute delete: $query", e)
        } finally {
            connection.close()
        }
    }

    protected void logError(String message, Exception e) {
        println("[ERROR]: $message\n${e.message}")
    }
}
