package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.PatientStatus;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Patient repo.
 */
public class PatientRepo implements Repository<Patient, Long> {

    @Override
    public Patient getById(Long id) throws SQLException {
        String query = "SELECT id, first_name, last_name, oib, birth_date, status, mbo, hospital_id, assigned_doctor_id FROM patient WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPatient(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Patient> getAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, oib, birth_date, status, mbo, hospital_id, assigned_doctor_id FROM patient";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                patients.add(mapPatient(rs));
            }
        }
        return patients;
    }

    @Override
    public void save(Patient entity) throws SQLException {
        String sql = "INSERT INTO patient (first_name, last_name, oib, birth_date, status, mbo, hospital_id, assigned_doctor_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getOib());
            ps.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));
            ps.setString(5, String.valueOf(entity.getStatus()));
            ps.setString(6, entity.getMbo());

            if (entity.getHospital() != null && entity.getHospital().getId() != null) {
                ps.setLong(7, entity.getHospital().getId());
            } else {
                ps.setNull(7, java.sql.Types.BIGINT);
            }

            if (entity.getAssignedDoctor() != null && entity.getAssignedDoctor().getId() != null) {
                ps.setLong(8, entity.getAssignedDoctor().getId());
            } else {
                ps.setNull(8, java.sql.Types.BIGINT);
            }

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
            conn.commit();
        }
    }

    @Override
    public void update(Patient entity) throws SQLException {
        String sql = "UPDATE patient SET first_name = ?, last_name = ?, oib = ?, birth_date = ?, status = ?, mbo = ?, hospital_id = ?, assigned_doctor_id = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getOib());
            ps.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));
            ps.setString(5, String.valueOf(entity.getStatus()));
            ps.setString(6, entity.getMbo());

            if (entity.getHospital() != null && entity.getHospital().getId() != null) {
                ps.setLong(7, entity.getHospital().getId());
            } else {
                ps.setNull(7, java.sql.Types.BIGINT);
            }

            if (entity.getAssignedDoctor() != null && entity.getAssignedDoctor().getId() != null) {
                ps.setLong(8, entity.getAssignedDoctor().getId());
            } else {
                ps.setNull(8, java.sql.Types.BIGINT);
            }

            ps.setLong(9, entity.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM patient WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Patient mapPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setFirstName(rs.getString("first_name"));
        patient.setLastName(rs.getString("last_name"));
        patient.setOib(rs.getString("oib"));
        patient.setBirthDate(rs.getDate("birth_date").toLocalDate());
        patient.setStatus(PatientStatus.valueOf(rs.getString("status")));
        patient.setMbo(rs.getString("mbo"));
        Hospital hospital = new Hospital();
        hospital.setId(rs.getLong("hospital_id"));
        patient.setHospital(hospital);

        Doctor doctor = new Doctor();
        doctor.setId(rs.getLong("assigned_doctor_id"));
        patient.setAssignedDoctor(doctor);

        return patient;
    }
}