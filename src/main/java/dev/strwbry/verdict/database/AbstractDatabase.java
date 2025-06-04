package dev.strwbry.verdict.database;

import java.sql.SQLException;

import dev.strwbry.verdict.utility.MsgUtility;

public abstract class AbstractDatabase implements Database {
    protected boolean connected = false;

    @Override
    public void connect() throws SQLException {
        try {
            // First establish the connection
            establishConnection();
            
            // Then initialize the schema
            initializeSchema();
            
            connected = true;
            MsgUtility.log("Successfully connected to database");
        } catch (SQLException e) {
            connected = false;
            MsgUtility.warning("<red>Failed to connect to database:</red>");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    // This method should be implemented by specific database implementations
    protected abstract void establishConnection() throws SQLException;

    private void initializeSchema() throws SQLException {
        try {
            MsgUtility.log("Starting schema initialization...");
            
            // Create players table
            MsgUtility.log("Creating players table...");
            executeUpdate("""
                CREATE TABLE IF NOT EXISTS `players` (
                    `uuid` varchar(255) PRIMARY KEY,
                    `username` varchar(255),
                    `timezone` varchar(255),
                    `join_date` date,
                    `hours_played` int,
                    `role` varchar(255)
                )
            """);
            MsgUtility.log("Players table created successfully");

            // Create player_ips table
            MsgUtility.log("Creating player_ips table...");
            executeUpdate("""
                CREATE TABLE IF NOT EXISTS `player_ips` (
                    `id` int AUTO_INCREMENT PRIMARY KEY,
                    `uuid` varchar(255),
                    `ip_address` varchar(255),
                    FOREIGN KEY (`uuid`) REFERENCES `players` (`uuid`)
                )
            """);
            MsgUtility.log("Player_ips table created successfully");

            // Create offense_definitions table
            MsgUtility.log("Creating offense_definitions table...");
            executeUpdate("""
                CREATE TABLE IF NOT EXISTS `offense_definitions` (
                    `offense_id` int AUTO_INCREMENT PRIMARY KEY,
                    `name` varchar(255),
                    `description` text,
                    `punishment_type` varchar(255),
                    `default_duration` int
                )
            """);
            MsgUtility.log("Offense_definitions table created successfully");

            // Create punishments table
            MsgUtility.log("Creating punishments table...");
            executeUpdate("""
                CREATE TABLE IF NOT EXISTS `punishments` (
                    `id` int AUTO_INCREMENT PRIMARY KEY,
                    `uuid` varchar(255),
                    `offense_id` int,
                    `custom_duration` int,
                    `date_assigned` datetime,
                    `assigned_by` varchar(255),
                    FOREIGN KEY (`uuid`) REFERENCES `players` (`uuid`),
                    FOREIGN KEY (`offense_id`) REFERENCES `offense_definitions` (`offense_id`),
                    FOREIGN KEY (`assigned_by`) REFERENCES `players` (`uuid`)
                )
            """);
            MsgUtility.log("Punishments table created successfully");

            // Create tickets table
            MsgUtility.log("Creating tickets table...");
            executeUpdate("""
                CREATE TABLE IF NOT EXISTS `tickets` (
                    `ticket_id` int AUTO_INCREMENT PRIMARY KEY,
                    `uuid` varchar(255),
                    `server` varchar(255),
                    `world` varchar(255),
                    `x` float,
                    `y` float,
                    `z` float,
                    `yaw` float,
                    `status` varchar(255),
                    `claimed_by` varchar(255),
                    `description` text,
                    `resulting_punishment` int,
                    FOREIGN KEY (`uuid`) REFERENCES `players` (`uuid`),
                    FOREIGN KEY (`claimed_by`) REFERENCES `players` (`uuid`),
                    FOREIGN KEY (`resulting_punishment`) REFERENCES `punishments` (`id`)
                )
            """);
            MsgUtility.log("Tickets table created successfully");

            MsgUtility.log("Database schema initialized successfully");
        } catch (SQLException e) {
            MsgUtility.warning("<red>Failed to initialize database schema:</red>");
            e.printStackTrace();
            throw e;
        }
    }
} 