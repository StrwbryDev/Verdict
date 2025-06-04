package dev.strwbry.verdict.database;

import dev.strwbry.verdict.Verdict;
import dev.strwbry.verdict.utility.MsgUtility;

public class DatabaseManager {
    private static Database database;

    public DatabaseManager() {
        initialize();
    }

    private void initialize() {
        String dbType = Verdict.getPlugin().getConfig().getString("database.type", "mysql").toLowerCase();
        
        try {
            switch (dbType) {
                case "mysql":
                    database = new MySQLDatabase();
                    break;
                case "sqlite":
                    //database = new SQLiteDatabase();
                    break;
                default:
                    MsgUtility.warning("<red>Unsupported database type: " + dbType + ". Defaulting to MySQL.</red>");
                    database = new MySQLDatabase();
                    break;
            }
            
            database.connect();
            MsgUtility.log("Successfully connected to " + dbType + " database");
        } catch (Exception e) {
            MsgUtility.warning("<red>Failed to initialize database:</red>");
            e.printStackTrace();
        }
    }

    public Database getDatabase() {
        if (database == null) {
            initialize();
        }
        return database;
    }

    public void shutdown() {
        if (database != null) {
            try {
                database.disconnect();
                database.close();
                MsgUtility.log("Database connection closed successfully");
            } catch (Exception e) {
                MsgUtility.warning("<red>Error closing database connection:</red>");
                e.printStackTrace();
            }
        }
    }
}
