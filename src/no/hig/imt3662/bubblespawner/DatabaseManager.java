package no.hig.imt3662.bubblespawner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Manages database access
 * Created by Martin on 14/09/25.
 */
public class DatabaseManager {
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    private String username;
    private String password;
    private String hostname;
    private String databaseName;
    private int port;

    /**
     * Prepares for connection to the MySQL database
     * @param username MySQL username
     * @param password MySQL password
     * @param dbName The name of the database we are going to use
     * @param hostname Hostname of the MySQL server
     * @param port Port for connecting to the MySQL server (default 3306)
     */
    public DatabaseManager(String username, String password, String dbName, String hostname, int port) {
        this.username = username;
        this.password = password;
        this.databaseName = dbName;
        this.hostname = hostname;
        this.port = port;

        Class<?> forName = null;
        try {
            forName = Class.forName("com.mysql.jdbc.Driver");
            String simpleName = forName.getSimpleName();
            LOGGER.info("MySQL driver loaded");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.severe("Failed to load MySQL driver");
        }
    }

    /**
     * Opens a connection that is ready for usage
     * @return An open connection towards MySQL
     * @throws SQLException Unable to connect to the MySQL server
     */
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.username);
        connectionProps.put("password", this.password);

        String connectionString = getConnectionString();
        conn = DriverManager.getConnection(connectionString, connectionProps);

        return conn;
    }

    private String getConnectionString() {
        return String.format("jdbc:mysql://%s:%s/%s", this.hostname, this.port, this.databaseName);
    }
}
