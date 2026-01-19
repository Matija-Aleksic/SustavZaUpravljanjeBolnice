package entity.repository.db;

import entity.Doctor;
import entity.repository.DoctorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorDbRepository implements DoctorRepository {
    private final Connection connection;

    public DoctorDbRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, date_of_birth, specialization, base_salary FROM doctors";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("specialization"),
                        rs.getDouble("base_salary")
                );
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    @Override
    public Optional<Doctor> findById(Integer id) {
        String sql = "SELECT id, first_name, last_name, date_of_birth, specialization, base_salary FROM doctors WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Doctor doctor = new Doctor(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("date_of_birth").toLocalDate(),
                            rs.getString("specialization"),
                            rs.getDouble("base_salary")
                    );
                    return Optional.of(doctor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Doctor entity) {
        String sql = "MERGE INTO doctors (id, first_name, last_name, date_of_birth, specialization, base_salary) KEY(id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getId());
            stmt.setString(2, entity.getFirstName());
            stmt.setString(3, entity.getLastName());
            stmt.setDate(4, java.sql.Date.valueOf(entity.getDateOfBirth()));
            stmt.setString(5, entity.getSpecialization());
            stmt.setDouble(6, entity.getBaseSalary());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAll(List<Doctor> entities) {
        for (Doctor doctor : entities) {
            save(doctor);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM doctors WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM doctors";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
