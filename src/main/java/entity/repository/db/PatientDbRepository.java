package entity.repository.db;

import entity.Patient;
import entity.repository.PatientRepository;

import java.sql.*;
import java.util.*;

public class PatientDbRepository implements PatientRepository {
    private final Connection connection;
    public PatientDbRepository(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Patient> findAll() {
        // TODO: Implement DB logic
        return new ArrayList<>();
    }
    @Override
    public Optional<Patient> findById(Integer id) {
        // TODO: Implement DB logic
        return Optional.empty();
    }
    @Override
    public void save(Patient entity) {
        // TODO: Implement DB logic
    }
    @Override
    public void saveAll(List<Patient> entities) {
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
