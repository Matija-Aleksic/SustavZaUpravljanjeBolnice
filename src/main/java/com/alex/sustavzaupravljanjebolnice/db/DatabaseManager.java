package com.alex.sustavzaupravljanjebolnice.db;

import com.alex.sustavzaupravljanjebolnice.entity.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.DoctorBuilder;
import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {
	private static final Properties props = new Properties();
	private static final String JDBC_URL;
	private static final String USER;
	private static final String PASS;
	private static Connection connection;

	static {
		try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties")) {
			if (input == null) throw new FileNotFoundException("Unable to find db.properties");
			props.load(input);
			JDBC_URL = props.getProperty("db.url");
			USER = props.getProperty("db.user");
			PASS = props.getProperty("db.password");
		} catch (IOException e) {
			throw new ExceptionInInitializerError("Failed to load db.properties: " + e.getMessage());
		}
	}

	private DatabaseManager() {
	}

	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(JDBC_URL, USER, PASS);
			connection.setAutoCommit(false);

			try {
				init();
			} catch (Exception e) {
				throw new SQLException("Database initialization failed", e);
			}
		}
		return connection;
	}

	private static void init() throws Exception {
		if (databaseExists()) {
			System.out.println("Database already exists. Using existing data.");
		} else {
			System.out.println("Creating new database...");
			runSqlScript("/db/schema.sql");
			runSqlScript("/db/seed.sql");
			connection.commit();
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
			if (is == null) throw new FileNotFoundException("Resource not found: " + resourcePath);

			String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			String[] statements = sql.split(";");
			try (Statement st = connection.createStatement()) {
				for (String s : statements) {
					String stmt = s.trim();
					if (!stmt.isEmpty()) st.execute(stmt);
				}
			}
		}
	}

	public static List<Doctor> getDoctors() throws SQLException {
		List<Doctor> doctors = new ArrayList<>();

		String sql = "SELECT * FROM STAFF WHERE role = 'DOCTOR'";


		try (PreparedStatement ps = connection.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				doctors.add(mapDoctor(rs));
			}
		}

		return doctors;
	}

	private static Doctor mapDoctor(ResultSet rs) throws SQLException {

		Long hospitalId = rs.getLong("hospital_id");
		Hospital hospital = null;


		return new DoctorBuilder()
				.setFirstName(rs.getString("first_name"))
				.setLastName(rs.getString("last_name"))
				.setOib(rs.getString("oib"))
				.setBirthDate(rs.getDate("birth_date").toLocalDate())
				.setRole(StaffRoles.valueOf(rs.getString("role")))
				.setEmail(rs.getString("email"))
				.setSalary(rs.getDouble("salary"))
				.setHospital(hospital)
				.setPhoneNumber(rs.getString("phone_number"))
				.setAddress(rs.getString("address"))
				.setAssignedPatients(new ArrayList<>())
				.setAppointments(new ArrayList<>())
				.createDoctor();
	}
}