package entity.repository.db;

import entity.repository.*;
import util.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;

public class DbRepositoryFactory implements DataRepositoryFactory {
    private final Connection connection;

    /**
     * Creates a DbRepositoryFactory using a provided connection.
     *
     * @param connection the database connection
     */
    public DbRepositoryFactory(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a DbRepositoryFactory using the DatabaseManager configuration.
     * Reads connection settings from database.properties file.
     *
     * @throws SQLException if connection fails
     */
    public DbRepositoryFactory() throws SQLException {
        this.connection = DatabaseManager.getConnection();
    }

    @Override
    public HospitalRepository getHospitalRepository() {
        return new HospitalDbRepository(connection);
    }

    @Override
    public DoctorRepository getDoctorRepository() {
        return new DoctorDbRepository(connection);
    }

    @Override
    public PatientRepository getPatientRepository() {
        return new PatientDbRepository(connection);
    }

    @Override
    public AppointmentRepository getAppointmentRepository() {
        return new AppointmentDbRepository(connection);
    }
}
