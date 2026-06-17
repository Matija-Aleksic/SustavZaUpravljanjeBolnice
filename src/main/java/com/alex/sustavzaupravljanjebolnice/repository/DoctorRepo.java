package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.builders.DoctorBuilder;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Doctor repo.
 */
public class DoctorRepo implements Repository<Doctor, Long> {

    @Override
    public Doctor getById(Long id) throws SQLException {
        String query = "SELECT id, first_name, last_name, oib, birth_date, role, permissions, email, salary, phone_number, address, hospital_id FROM STAFF WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDoctor(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Doctor> getAll() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, oib, birth_date, role, permissions, email, salary, phone_number, address, hospital_id FROM STAFF WHERE role = 'DOCTOR'";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                doctors.add(mapDoctor(rs));
            }
        }
        return doctors;
    }

    @Override
    public void save(Doctor entity) throws SQLException {
        String sql = "INSERT INTO staff (first_name, last_name, oib, birth_date, role, email, salary, hospital_id, phone_number, address) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getOib());
            ps.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));
            ps.setString(5, entity.getRole().name());
            ps.setString(6, entity.getEmail());
            ps.setDouble(7, entity.getSalary());

            if (entity.getHospital() != null && entity.getHospital().getId() != null) {
                ps.setLong(8, entity.getHospital().getId());
            } else {
                ps.setNull(8, java.sql.Types.BIGINT);
            }

            ps.setString(9, entity.getPhoneNumber());
            ps.setString(10, entity.getAddress());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
            conn.commit();
        }
    }

    @Override
    public void update(Doctor entity) throws SQLException {
        String sql = "UPDATE STAFF SET first_name = ?, last_name = ?, oib = ?, birth_date = ?, role = ?, email = ?, salary = ?, hospital_id = ?, phone_number = ?, address = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getOib());
            ps.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));
            ps.setString(5, entity.getRole().name());
            ps.setString(6, entity.getEmail());
            ps.setDouble(7, entity.getSalary());

            if (entity.getHospital() != null && entity.getHospital().getId() != null) {
                ps.setLong(8, entity.getHospital().getId());
            } else {
                ps.setNull(8, java.sql.Types.BIGINT);
            }

            ps.setString(9, entity.getPhoneNumber());
            ps.setString(10, entity.getAddress());
            ps.setLong(11, entity.getId());
            ps.executeUpdate();

            conn.commit();
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM STAFF WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            conn.commit();
        }
    }

    private Doctor mapDoctor(ResultSet rs) throws SQLException {
        Hospital hospital = null;
        long hospitalId = rs.getLong("hospital_id");
        if (!rs.wasNull()) {
            hospital = new Hospital();
            hospital.setId(hospitalId);
        }
        return new DoctorBuilder().setId(rs.getInt("id")).setFirstName(rs.getString("first_name")).setLastName(rs.getString("last_name")).setOib(rs.getString("oib")).setBirthDate(rs.getDate("birth_date").toLocalDate()).setRole(StaffRoles.valueOf(rs.getString("role"))).setEmail(rs.getString("email")).setSalary(rs.getDouble("salary")).setHospital(hospital).setRole(StaffRoles.DOCTOR).setPhoneNumber(rs.getString("phone_number")).setAddress(rs.getString("address")).setAssignedPatients(new ArrayList<>()).setAppointments(new ArrayList<>()).build();
    }
}