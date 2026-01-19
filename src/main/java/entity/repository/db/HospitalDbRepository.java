package entity.repository.db;

import entity.Hospital;
import entity.repository.HospitalRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HospitalDbRepository implements HospitalRepository {
    private final Connection connection;

    public HospitalDbRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Hospital> findAll() {
        List<Hospital> hospitals = new ArrayList<>();
        String sql = "SELECT id, name FROM hospitals";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Hospital hospital = new Hospital(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                hospitals.add(hospital);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hospitals;
    }

    @Override
    public Optional<Hospital> findById(Integer id) {
        String sql = "SELECT id, name FROM hospitals WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Hospital hospital = new Hospital(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                    return Optional.of(hospital);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Hospital entity) {
        String sql = "MERGE INTO hospitals (id, name) KEY(id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getId());
            stmt.setString(2, entity.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAll(List<Hospital> entities) {
        for (Hospital hospital : entities) {
            save(hospital);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM hospitals WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM hospitals";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
