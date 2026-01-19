package entity.repository.db;

import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.repository.AppointmentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppointmentDbRepository implements AppointmentRepository {
    private final Connection connection;

    public AppointmentDbRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.id, a.doctor_id, a.patient_id, a.date_time, " +
                "d.id as d_id, d.first_name as d_first_name, d.last_name as d_last_name, d.date_of_birth as d_dob, d.specialization, d.base_salary, " +
                "p.id as p_id, p.first_name as p_first_name, p.last_name as p_last_name, p.date_of_birth as p_dob, p.condition, p.insurance_number " +
                "FROM appointments a " +
                "JOIN doctors d ON a.doctor_id = d.id " +
                "JOIN patients p ON a.patient_id = p.id";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getInt("d_id"),
                        rs.getString("d_first_name"),
                        rs.getString("d_last_name"),
                        rs.getDate("d_dob").toLocalDate(),
                        rs.getString("specialization"),
                        rs.getDouble("base_salary")
                );
                Patient patient = new Patient.Builder(
                        rs.getInt("p_id"),
                        rs.getString("p_first_name"),
                        rs.getString("p_last_name"),
                        rs.getDate("p_dob").toLocalDate()
                )
                        .condition(rs.getString("condition"))
                        .insuranceNumber(rs.getString("insurance_number"))
                        .build();

                Appointment appointment = new Appointment(
                        rs.getInt("id"),
                        doctor,
                        patient,
                        rs.getTimestamp("date_time").toLocalDateTime()
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public Optional<Appointment> findById(Integer id) {
        String sql = "SELECT a.id, a.doctor_id, a.patient_id, a.date_time, " +
                "d.id as d_id, d.first_name as d_first_name, d.last_name as d_last_name, d.date_of_birth as d_dob, d.specialization, d.base_salary, " +
                "p.id as p_id, p.first_name as p_first_name, p.last_name as p_last_name, p.date_of_birth as p_dob, p.condition, p.insurance_number " +
                "FROM appointments a " +
                "JOIN doctors d ON a.doctor_id = d.id " +
                "JOIN patients p ON a.patient_id = p.id " +
                "WHERE a.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Doctor doctor = new Doctor(
                            rs.getInt("d_id"),
                            rs.getString("d_first_name"),
                            rs.getString("d_last_name"),
                            rs.getDate("d_dob").toLocalDate(),
                            rs.getString("specialization"),
                            rs.getDouble("base_salary")
                    );
                    Patient patient = new Patient.Builder(
                            rs.getInt("p_id"),
                            rs.getString("p_first_name"),
                            rs.getString("p_last_name"),
                            rs.getDate("p_dob").toLocalDate()
                    )
                            .condition(rs.getString("condition"))
                            .insuranceNumber(rs.getString("insurance_number"))
                            .build();

                    Appointment appointment = new Appointment(
                            rs.getInt("id"),
                            doctor,
                            patient,
                            rs.getTimestamp("date_time").toLocalDateTime()
                    );
                    return Optional.of(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Appointment entity) {
        String sql = "MERGE INTO appointments (id, doctor_id, patient_id, date_time) KEY(id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.id());
            stmt.setInt(2, entity.doctor().getId());
            stmt.setInt(3, entity.patient().getId());
            stmt.setTimestamp(4, Timestamp.valueOf(entity.dateTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAll(List<Appointment> entities) {
        for (Appointment appointment : entities) {
            save(appointment);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM appointments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM appointments";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
