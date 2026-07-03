package com.alex.sustavzaupravljanjebolnice.db;

import com.alex.sustavzaupravljanjebolnice.entity.exception.DatabaseInitializationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Database manager with automatic local fallback functionality.
 */
public class DatabaseManager {

    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    private static final Properties props = new Properties();

    private static final String jdbcUrl;
    private static final String dbUser;
    private static final String dbPass;
    private static boolean isUsingFallback = false;

    static {
        try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new FileNotFoundException("Unable to find db.properties");
            }

            props.load(input);

            String remoteUrl = props.getProperty("db.remote.url");
            String remoteUser = props.getProperty("db.remote.user");
            String remotePass = props.getProperty("db.remote.password");

            String localUrl = props.getProperty("db.local.url");
            String localUser = props.getProperty("db.local.user");
            String localPass = props.getProperty("db.local.password");

            // Prevent application freeze during down times by forcing a 3 second timeout threshold
            DriverManager.setLoginTimeout(3);

            logger.info("Attempting connection to remote H2 database: " + remoteUrl);
            if (testConnection(remoteUrl, remoteUser, remotePass)) {
                jdbcUrl = remoteUrl;
                dbUser = remoteUser;
                dbPass = remotePass;
                isUsingFallback = false;
                logger.info("Successfully established connection to REMOTE database server.");
            } else {
                jdbcUrl = localUrl;
                dbUser = localUser;
                dbPass = localPass;
                isUsingFallback = true;
                logger.warning("Remote database server unreachable. Failing over to LOCAL file database.");
            }

            initDatabase();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize DatabaseManager during application startup", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    private DatabaseManager() {
    }

    /**
     * Helper method to verify data resource accessibility without breaking execution.
     */
    private static boolean testConnection(String url, String user, String pass) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            return conn.isValid(2);
        } catch (SQLException e) {
            logger.log(Level.FINE, "Remote database handshake failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
    }

    /**
     * Identifies if application is currently reading/writing to the local fallback cache.
     *
     * @return true if using fallback, false if running on live remote database.
     */
    public static boolean isUsingFallbackDatabase() {
        return isUsingFallback;
    }

    private static void initDatabase() throws DatabaseInitializationException {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            if (databaseExists(conn)) {
                logger.info("Database objects already provisioned. Skipping generation.");
                return;
            }

            runSqlScript(conn, "/db/schema.sql");
            runSqlScript(conn, "/db/seed.sql");

            conn.commit();
            logger.info("Database tables and seed values created successfully.");

        } catch (Exception e) {
            throw new DatabaseInitializationException("Failed to automatically build or verify data structures", e);
        }
    }

    private static boolean databaseExists(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getTables(null, "PUBLIC", "HOSPITAL", null)) {
            return rs.next();
        }
    }

    private static void runSqlScript(Connection conn, String resourcePath) throws IOException, SQLException {
        try (InputStream is = DatabaseManager.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }

            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            try (Statement st = conn.createStatement()) {
                String[] statements = sql.split(";\\s*\\r?\\n");
                for (String stmt : statements) {
                    String s = stmt.trim();
                    if (!s.isEmpty()) {
                        st.execute(s);
                    }
                }
            }
        }
    }
}