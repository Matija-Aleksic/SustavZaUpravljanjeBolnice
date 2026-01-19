package entity.repository.db;

import entity.Doctor;
import entity.repository.DoctorRepository;

import java.sql.*;
import java.util.*;

public class DoctorDbRepository implements DoctorRepository {
    private final Connection connection;
    public DoctorDbRepository(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Doctor> findAll() {
        // TODO: Implement DB logic
        return new ArrayList<>();
    }
    @Override
    public Optional<Doctor> findById(Integer id) {
        // TODO: Implement DB logic
        return Optional.empty();
    }
    @Override
    public void save(Doctor entity) {
        // TODO: Implement DB logic
    }
    @Override
    public void saveAll(List<Doctor> entities) {
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
