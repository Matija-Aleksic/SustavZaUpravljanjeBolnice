package entity.repository.db;

import entity.Appointment;
import entity.repository.AppointmentRepository;

import java.sql.*;
import java.util.*;

public class AppointmentDbRepository implements AppointmentRepository {
    private final Connection connection;
    public AppointmentDbRepository(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Appointment> findAll() {
        // TODO: Implement DB logic
        return new ArrayList<>();
    }
    @Override
    public Optional<Appointment> findById(Integer id) {
        // TODO: Implement DB logic
        return Optional.empty();
    }
    @Override
    public void save(Appointment entity) {
        // TODO: Implement DB logic
    }
    @Override
    public void saveAll(List<Appointment> entities) {
        // TODO: Implement DB logic
    }
    @Override
    public void deleteById(Integer id) {
        // TODO: Implement DB logic
    }
    @Override
    public void deleteAll() {
        // TODO: Implement DB logic
    }
}
