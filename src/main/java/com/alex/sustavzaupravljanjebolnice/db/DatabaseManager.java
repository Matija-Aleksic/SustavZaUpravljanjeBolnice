package com.alex.sustavzaupravljanjebolnice.db;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import static java.lang.Thread.sleep;

/**
 * DatabaseManager that initializes an H2 database using SQL scripts.
 *
 * On first run: Creates tables and seeds data
 * On subsequent runs: Uses existing database and preserves all changes
 *
 * SQL scripts:
 * - `src/main/resources/db/schema.sql` (creates tables)
 * - `src/main/resources/db/seed.sql` (initial data)
 */
public class DatabaseManager {
	private static final String JDBC_URL = "jdbc:h2:./testdb;DB_CLOSE_DELAY=-1;FILE_LOCK=NO";
	private static final String USER = "sa";
	private static final String PASS = "";
	private static Connection connection;

	static {
		try {
			init();
		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize database", e);
		}
	}

	private static void init() throws Exception {
		connection = DriverManager.getConnection(JDBC_URL, USER, PASS);
		connection.setAutoCommit(false);
		sleep(500);

		try {
			if (databaseExists()) {
				System.out.println("Database already exists. Using existing data.");
			} else {
				System.out.println("Creating new database...");
				runSqlScript("/db/schema.sql");
				System.out.println("Database schema initialized successfully.");

				runSqlScript("/db/seed.sql");
				System.out.println("Database seed data inserted successfully.");

				connection.commit();

			}
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}

	private static boolean databaseExists() throws SQLException {
		DatabaseMetaData metaData = connection.getMetaData();
		try (ResultSet tables = metaData.getTables(null, "PUBLIC", "HOSPITAL", null)) {
			return tables.next();
		}
	}

	private static void runSqlScript(String resourcePath) throws Exception {
		try (InputStream is = DatabaseManager.class.getResourceAsStream(resourcePath)) {
			if (is == null) {
				System.out.println("Resource not found: " + resourcePath);
				return;
			}
			String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			String[] statements = sql.split(";");
			try (Statement st = connection.createStatement()) {
				for (String s : statements) {
					String stmt = s.trim();
					if (stmt.isEmpty()) continue;
					try {
						st.execute(stmt);
						System.out.println("✓ Executed: " + stmt.substring(0, Math.min(50, stmt.length())) + "...");
					} catch (SQLException e) {
						System.err.println("✗ Failed: " + stmt);
						e.printStackTrace();
						throw e;
					}
				}
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(JDBC_URL, USER, PASS);
			connection.setAutoCommit(false);
		}
		return connection;
	}
}