package entity.repository.db;

import entity.Hospital;
import entity.repository.HospitalRepository;

import java.sql.*;
import java.util.*;

public class HospitalDbRepository implements HospitalRepository {
    private final Connection connection;
    public HospitalDbRepository(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Hospital> findAll() {
        // TODO: Implement DB logic
        return new ArrayList<>();
    }
    @Override
    public Optional<Hospital> findById(Integer id) {
        // TODO: Implement DB logic
        return Optional.empty();
    }
    @Override
    public void save(Hospital entity) {
        // TODO: Implement DB logic
    }
    @Override
    public void saveAll(List<Hospital> entities) {
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
