package dev.strwbry.verdict.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDatabase extends AbstractDatabase {
    private Connection connection;

    @Override
    public void close() throws SQLException {}

    @Override
    public void disconnect() throws SQLException {}

    @Override
    public java.sql.ResultSet executeQuery(String query) throws SQLException { return null; }

    @Override
    public int executeUpdate(String query) throws SQLException { return 0; }

    @Override
    protected void establishConnection() throws SQLException {}
}