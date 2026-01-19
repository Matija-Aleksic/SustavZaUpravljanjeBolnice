package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Database connection manager that reads configuration from properties file.
 */
public final class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static final String PROPERTIES_FILE = "/database.properties";

    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    private static Connection connection;

    static {
        loadProperties();
    }

    private DatabaseManager() {
        throw new RuntimeException("DatabaseManager should not be instantiated");
    }

    /**
     * Loads database properties from the properties file.
     */
    private static void loadProperties() {
        try (InputStream input = DatabaseManager.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                logger.error("Unable to find database.properties");
                throw new RuntimeException("Unable to find database.properties");
            }

            Properties props = new Properties();
            props.load(input);

            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");

            if (driver != null && !driver.isEmpty()) {
                Class.forName(driver);
            }

            logger.info("Database properties loaded successfully");
        } catch (IOException e) {
            logger.error("Error loading database properties", e);
            throw new RuntimeException("Error loading database properties", e);
        } catch (ClassNotFoundException e) {
            logger.error("Database driver not found", e);
            throw new RuntimeException("Database driver not found", e);
        }
    }

    /**
     * Gets a database connection. Creates a new one if needed.
     *
     * @return the database connection
     * @throws SQLException if connection fails
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
            logger.info("Database connection established");
        }
        return connection;
    }

    /**
     * Closes the database connection.
     */
    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.error("Error closing database connection", e);
            }
            connection = null;
        }
    }

    /**
     * Gets the database URL.
     *
     * @return the URL
     */
    public static String getUrl() {
        return url;
    }

    /**
     * Gets the database username.
     *
     * @return the username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Initializes the database schema from schema.sql file.
     */
    public static void initializeSchema() {
        try (InputStream input = DatabaseManager.class.getResourceAsStream("/schema.sql")) {
            if (input == null) {
                logger.warn("schema.sql not found, skipping schema initialization");
                return;
            }

            String sql = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            Connection conn = getConnection();
            try (Statement stmt = conn.createStatement()) {
                for (String statement : sql.split(";")) {
                    String trimmed = statement.trim();
                    if (!trimmed.isEmpty() && !trimmed.startsWith("--")) {
                        stmt.execute(trimmed);
                    }
                }
                logger.info("Database schema initialized successfully");
            }
            // Don't close connection - it's a singleton managed by DatabaseManager
        } catch (IOException | SQLException e) {
            logger.error("Error initializing database schema", e);
            throw new RuntimeException("Error initializing database schema", e);
        }
    }
}
