package com.alex.sustavzaupravljanjebolnice.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());

    private static final Properties props = new Properties();

    private static final String JDBC_URL;
    private static final String USER;
    private static final String PASS;

    static {
        try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new FileNotFoundException("Unable to find db.properties");
            }

            props.load(input);

            JDBC_URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASS = props.getProperty("db.password");

            initDatabase();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize DatabaseManager", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    private DatabaseManager() {
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASS);
        conn.setAutoCommit(false);
        return conn;
    }

    private static void initDatabase() {
        try (Connection conn = getConnection()) {

            if (databaseExists(conn)) {
                logger.info("Database already initialized");
                return;
            }

            logger.info("Creating database schema + seed data");

            runSqlScript(conn, "/db/schema.sql");
            runSqlScript(conn, "/db/seed.sql");

            conn.commit();
            logger.info("Database initialized successfully");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Database initialization failed", e);
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