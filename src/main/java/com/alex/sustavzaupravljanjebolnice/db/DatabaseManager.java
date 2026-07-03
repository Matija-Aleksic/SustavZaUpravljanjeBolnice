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
 * The type Database manager.
 */
public class DatabaseManager {

    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    private static final Properties props = new Properties();

    private static final String JDBC_URL;
    private static final String DB_USER;
    private static final String DB_PASS;
    private static boolean isUsingFallback = false;

    static {
        try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new FileNotFoundException("db.properties not found");
            }

            props.load(input);

            String remoteUrl = props.getProperty("db.remote.url");
            String remoteUser = props.getProperty("db.remote.user");
            String remotePass = props.getProperty("db.remote.password");

            String localUrl = props.getProperty("db.local.url");
            String localUser = props.getProperty("db.local.user");
            String localPass = props.getProperty("db.local.password");

            DriverManager.setLoginTimeout(3);

            logger.info("Trying remote DB...");
            if (testConnection(remoteUrl, remoteUser, remotePass)) {

                JDBC_URL = remoteUrl;
                DB_USER = remoteUser;
                DB_PASS = remotePass;
                isUsingFallback = false;

                logger.info("Connected to REMOTE DB");
            } else {

                logger.warning("Remote unavailable. Using LOCAL H2 (AUTO_SERVER mode)");

                JDBC_URL = localUrl;
                DB_USER = localUser;
                DB_PASS = localPass;
                isUsingFallback = true;
            }

            initDatabase();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Database init failed", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    private DatabaseManager() {
    }

    private static boolean testConnection(String url, String user, String pass) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            return conn.isValid(2);
        } catch (SQLException _) {
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
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }

    /**
     * Is using fallback database boolean.
     *
     * @return the boolean
     */
    public static boolean isUsingFallbackDatabase() {
        return isUsingFallback;
    }

    private static void initDatabase() throws DatabaseInitializationException {
        try (Connection conn = getConnection()) {

            conn.setAutoCommit(false);

            if (databaseExists(conn)) {
                logger.info("DB already initialized");
                return;
            }

            runSql(conn, "/db/schema.sql");
            runSql(conn, "/db/seed.sql");

            conn.commit();

        } catch (Exception e) {
            throw new DatabaseInitializationException("DB init failed", e);
        }
    }

    private static boolean databaseExists(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getTables(null, "PUBLIC", "HOSPITAL", null)) {
            return rs.next();
        }
    }

    private static void runSql(Connection conn, String path) throws IOException, SQLException {

        try (InputStream is = DatabaseManager.class.getResourceAsStream(path)) {

            if (is == null) {
                throw new FileNotFoundException("Missing: " + path);
            }

            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            try (Statement st = conn.createStatement()) {
                for (String stmt : sql.split(";\\s*\\n")) {
                    stmt = stmt.trim();
                    if (!stmt.isEmpty()) {
                        st.execute(stmt);
                    }
                }
            }
        }
    }
}