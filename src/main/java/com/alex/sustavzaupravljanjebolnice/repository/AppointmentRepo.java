package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Appointment repo.
 */
public class AppointmentRepo implements Repository<Appointment, Long> {

    @Override
    public Appointment getById(Long id) throws SQLException {
        String query = "SELECT id, doctor_id, patient_id, date_time FROM appointment WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapAppointment(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Appointment> getAll() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT id, doctor_id, patient_id, date_time FROM appointment";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                appointments.add(mapAppointment(rs));
            }
        }
        return appointments;
    }

    @Override
    public void save(Appointment entity) throws SQLException {
        String sql = "INSERT INTO appointment (doctor_id, patient_id, date_time) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, entity.doctorId());
            ps.setLong(2, entity.patientId());
            ps.setTimestamp(3, Timestamp.valueOf(entity.dateTime()));

            ps.executeUpdate();
            conn.commit();
        }
    }

    @Override
    public void update(Appointment entity) throws SQLException {
        String sql = "UPDATE appointment SET doctor_id = ?, patient_id = ?, date_time = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, entity.doctorId());
            ps.setLong(2, entity.patientId());
            ps.setTimestamp(3, Timestamp.valueOf(entity.dateTime()));
            ps.setLong(4, entity.id());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM appointment WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Appointment mapAppointment(ResultSet rs) throws SQLException {
        return new Appointment(rs.getInt("id"), rs.getInt("doctor_id"), rs.getInt("patient_id"), rs.getTimestamp("date_time").toLocalDateTime());
    }
}