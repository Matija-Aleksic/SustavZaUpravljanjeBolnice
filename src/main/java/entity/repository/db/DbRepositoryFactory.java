package entity.repository.db;

import entity.repository.*;

import java.sql.Connection;

public class DbRepositoryFactory implements DataRepositoryFactory {
    private final Connection connection;

    public DbRepositoryFactory(Connection connection) {
        this.connection = connection;
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
