package dev.strwbry.verdict.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Database {
    /**
     * Establishes a connection to the database and initializes the schema.
     * @throws SQLException if the connection fails
     */
    void connect() throws SQLException;

    /**
     * Closes the active database connection.
     * @throws SQLException if the disconnection fails
     */
    void disconnect() throws SQLException;

    /**
     * Executes a query and returns the result set.
     * @param query the SQL query to execute
     * @return the result set of the query
     * @throws SQLException if the query execution fails
     */
    ResultSet executeQuery(String query) throws SQLException;

    /**
     * Executes an update query (INSERT, UPDATE, DELETE).
     * @param query the SQL query to execute
     * @return the number of rows affected
     * @throws SQLException if the query execution fails
     */
    int executeUpdate(String query) throws SQLException;

    /**
     * Closes all database resources.
     * @throws SQLException if the cleanup fails
     */
    void close() throws SQLException;

    /**
     * Checks if the database connection is active.
     * @return true if connected, false otherwise
     */
    boolean isConnected();
}
