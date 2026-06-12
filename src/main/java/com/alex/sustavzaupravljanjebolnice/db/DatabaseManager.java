package com.alex.sustavzaupravljanjebolnice.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Properties;

/**
 * The type Database manager.
 */
public class DatabaseManager {
    private static final Properties props = new Properties();
    private static final String JDBC_URL;
    private static final String USER;
    private static final String PASS;

    static {
        try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) throw new FileNotFoundException("Unable to find db.properties");
            props.load(input);
            JDBC_URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASS = props.getProperty("db.password");

            initDatabase();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to initialize database: " + e.getMessage());
        }
    }

    private DatabaseManager() {
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASS);
    }

    private static void initDatabase() {
        try (Connection conn = getConnection()) {
            if (databaseExists(conn)) {
                System.out.println("Database already exists. Using existing data.");
            } else {
                System.out.println("Creating new database...");
                runSqlScript(conn, "/db/schema.sql");
                runSqlScript(conn, "/db/seed.sql");
                conn.commit();
            }

        } catch (IOException | SQLException e) {
            e.getMessage();
        }
    }

    private static boolean databaseExists(Connection conn) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet tables = metaData.getTables(null, "PUBLIC", "HOSPITAL", null)) {
            return tables.next();
        }
    }

    private static void runSqlScript(Connection conn, String resourcePath) throws IOException, SQLException {
        try (InputStream is = DatabaseManager.class.getResourceAsStream(resourcePath)) {
            if (is == null) throw new FileNotFoundException("Resource not found: " + resourcePath);

            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            String[] statements = sql.split(";");
            try (Statement st = conn.createStatement()) {
                for (String s : statements) {
                    String stmt = s.trim();
                    if (!stmt.isEmpty()) st.execute(stmt);
                }
            }
        }
    }

}