package entity.repository.db;

import entity.Patient;
import entity.repository.PatientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientDbRepository implements PatientRepository {
    private final Connection connection;
    public PatientDbRepository(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, date_of_birth, condition, insurance_number FROM patients";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Patient patient = new Patient.Builder(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("date_of_birth").toLocalDate()
                )
                        .condition(rs.getString("condition"))
                        .insuranceNumber(rs.getString("insurance_number"))
                        .build();
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
    @Override
    public Optional<Patient> findById(Integer id) {
        String sql = "SELECT id, first_name, last_name, date_of_birth, condition, insurance_number FROM patients WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Patient patient = new Patient.Builder(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("date_of_birth").toLocalDate()
                    )
                            .condition(rs.getString("condition"))
                            .insuranceNumber(rs.getString("insurance_number"))
                            .build();
                    return Optional.of(patient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public void save(Patient entity) {
        String sql = "MERGE INTO patients (id, first_name, last_name, date_of_birth, condition, insurance_number) KEY(id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getId());
            stmt.setString(2, entity.getFirstName());
            stmt.setString(3, entity.getLastName());
            stmt.setDate(4, java.sql.Date.valueOf(entity.getDateOfBirth()));
            stmt.setString(5, entity.getConditionName());
            stmt.setString(6, entity.getInsuranceNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveAll(List<Patient> entities) {
        for (Patient patient : entities) {
            save(patient);
        }
    }
    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteAll() {
        String sql = "DELETE FROM patients";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
