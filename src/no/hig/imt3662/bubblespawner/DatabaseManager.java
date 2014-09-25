package no.hig.imt3662.bubblespawner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Martin on 14/09/25.
 */
public class DatabaseManager {
    private String username;
    private String password;
    private String hostname;
    private String databaseName;
    private int port;

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
            MainEnvironment.getDefaultLogger().info("MySQL driver loaded");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            MainEnvironment.getDefaultLogger().severe("Failed to load MySQL driver");
        }
    }

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
