package dev.strwbry.verdict.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.strwbry.verdict.Verdict;
import dev.strwbry.verdict.utility.MsgUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDatabase extends AbstractDatabase {
    private HikariDataSource dataSource;
    private Connection connection;

    @Override
    protected void establishConnection() throws SQLException {
        HikariConfig config = new HikariConfig();
        
        // Get database configuration from config.yml
        String host = Verdict.getPlugin().getConfig().getString("database.host", "localhost");
        int port = Verdict.getPlugin().getConfig().getInt("database.port", 3306);
        String database = Verdict.getPlugin().getConfig().getString("database.name", "verdict");
        String username = Verdict.getPlugin().getConfig().getString("database.username", "root");
        String password = Verdict.getPlugin().getConfig().getString("database.password", "");

        // Log the configuration values (excluding password for security)
        MsgUtility.log("Database configuration:");
        MsgUtility.log("Host: " + host);
        MsgUtility.log("Port: " + port);
        MsgUtility.log("Database: " + database);
        MsgUtility.log("Username: " + username);
        MsgUtility.log("Password length: " + (password != null ? password.length() : 0));

        // Configure HikariCP
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(300000);
        config.setConnectionTimeout(10000);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        // Create the data source
        dataSource = new HikariDataSource(config);
        
        // Get a connection and create the database if it doesn't exist
        try (Connection tempConn = dataSource.getConnection()) {
            tempConn.createStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS `" + database + "`");
            MsgUtility.log("Database '" + database + "' created or already exists");
        }

        // Get the main connection that will be used for operations
        connection = dataSource.getConnection();
        connected = true;
        MsgUtility.log("Successfully established database connection");
    }

    @Override
    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
            connected = false;
            MsgUtility.log("MySQL connection closed");
        }
    }

    @Override
    public ResultSet executeQuery(String query) throws SQLException {
        if (!isConnected()) {
            throw new SQLException("No active database connection");
        }
        return connection.createStatement().executeQuery(query);
    }

    @Override
    public int executeUpdate(String query) throws SQLException {
        if (!isConnected()) {
            throw new SQLException("No active database connection");
        }
        try {
            int result = connection.createStatement().executeUpdate(query);
            MsgUtility.log("Successfully executed update query");
            return result;
        } catch (SQLException e) {
            MsgUtility.warning("<red>Failed to execute update query:</red>");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void close() throws SQLException {
        disconnect();
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
            MsgUtility.log("MySQL connection pool closed");
        }
    }
}
