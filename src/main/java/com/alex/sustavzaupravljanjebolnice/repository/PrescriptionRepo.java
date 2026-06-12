package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.Prescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepo implements Repository<Prescription, String> {

    @Override
    public Prescription getById(String id) throws SQLException {
        String query = "SELECT * FROM prescription WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPrescription(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Prescription> getAll() throws SQLException {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM prescription";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                prescriptions.add(mapPrescription(rs));
            }
        }
        return prescriptions;
    }

    @Override
    public void save(Prescription entity) throws SQLException {
        String sql = "INSERT INTO prescription (id, name, description, doctor_id, patient_id, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getId());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getDescription());
            ps.setLong(4, entity.getDoctorId());
            ps.setLong(5, entity.getPatientId());
            ps.setDate(6, java.sql.Date.valueOf(entity.getStartDate()));
            ps.setDate(7, java.sql.Date.valueOf(entity.getEndDate()));

            ps.executeUpdate();
            conn.commit();
        }
    }

    @Override
    public void update(Prescription entity) throws SQLException {
        String sql = "UPDATE prescription SET name = ?, description = ?, doctor_id = ?, patient_id = ?, start_date = ?, end_date = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setLong(3, entity.getDoctorId());
            ps.setLong(4, entity.getPatientId());
            ps.setDate(5, java.sql.Date.valueOf(entity.getStartDate()));
            ps.setDate(6, java.sql.Date.valueOf(entity.getEndDate()));
            ps.setString(7, entity.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(String id) throws SQLException {
        String sql = "DELETE FROM prescription WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    private Prescription mapPrescription(ResultSet rs) throws SQLException {
        Prescription prescription = new Prescription();
        prescription.setId(rs.getString("id"));
        prescription.setName(rs.getString("name"));
        prescription.setDescription(rs.getString("description"));
        prescription.setStartDate(rs.getDate("start_date").toLocalDate());
        prescription.setEndDate(rs.getDate("end_date").toLocalDate());
        prescription.setDoctorId(rs.getInt("doctor_id"));
        prescription.setPatientId(rs.getInt("patient_id"));

        return prescription;
    }
}