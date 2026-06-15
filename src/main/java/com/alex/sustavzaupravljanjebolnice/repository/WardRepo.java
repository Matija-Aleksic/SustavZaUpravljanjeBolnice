package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.PatientStatus;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Department;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Ward repo.
 */
public class WardRepo implements Repository<Ward, Long> {

    @Override
    public Ward getById(Long id) throws SQLException {
        String query = "SELECT id, name, max_capacity, capacity, department_id FROM ward WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ward ward = mapWard(rs);
                    ward.setPatients(getPatientsForWard(conn, id));
                    conn.commit();
                    return ward;
                }
            }
            conn.commit();
        }
        return null;
    }

    @Override
    public List<Ward> getAll() throws SQLException {
        List<Ward> wards = new ArrayList<>();
        String sql = "SELECT id, name, max_capacity, capacity, department_id, NURSE_ID FROM ward";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ward ward = mapWard(rs);
                wards.add(ward);
            }

            for (Ward w : wards) {
                w.setPatients(getPatientsForWard(conn, w.getId()));
            }

            conn.commit();
        }
        return wards;
    }

    @Override
    public void save(Ward entity) throws SQLException {
        String sql = "INSERT INTO ward (name, max_capacity, capacity, department_id, nurse_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getMaxCapacity());
            ps.setInt(3, entity.getCapacity());
            ps.setLong(4, entity.getDepartment().getId());

            if (entity.getNurseId() != null) {
                ps.setLong(5, entity.getNurseId());
            } else {
                ps.setNull(5, Types.BIGINT);
            }

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
    public void update(Ward entity) throws SQLException {
        String sql = "UPDATE ward SET name = ?, max_capacity = ?, capacity = ?, department_id = ?, nurse_id = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getMaxCapacity());
            ps.setInt(3, entity.getCapacity());
            ps.setLong(4, entity.getDepartment().getId());

            if (entity.getNurseId() != null) {
                ps.setLong(5, entity.getNurseId());
            } else {
                ps.setNull(5, Types.BIGINT);
            }

            ps.setLong(6, entity.getId());

            ps.executeUpdate();
            conn.commit();
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM ward WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
            conn.commit();
        }
    }

    private Ward mapWard(ResultSet rs) throws SQLException {
        Ward ward = new Ward();
        ward.setId(rs.getLong("id"));
        ward.setName(rs.getString("name"));
        ward.setMaxCapacity(rs.getInt("max_capacity"));
        ward.setCapacity(rs.getInt("capacity"));

        long nurseId = rs.getLong("nurse_id");
        if (!rs.wasNull()) {
            ward.setNurseId(nurseId);
        } else {
            ward.setNurseId(null);
        }

        Department department = new Department();
        department.setId(rs.getLong("department_id"));
        ward.setDepartment(department);

        return ward;
    }

    private List<Patient> getPatientsForWard(Connection conn, Long wardId) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, oib, birth_date, status, mbo FROM patient WHERE ward_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, wardId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Patient p = new Patient();
                    p.setId(rs.getInt("id"));
                    p.setFirstName(rs.getString("first_name"));
                    p.setLastName(rs.getString("last_name"));
                    p.setOib(rs.getString("oib"));
                    p.setStatus(PatientStatus.valueOf(rs.getString("status")));
                    p.setMbo(rs.getString("mbo"));
                    Ward flatWard = new Ward();
                    flatWard.setId(wardId);
                    p.setAssignedWard(flatWard);

                    patients.add(p);
                }
            }
        }
        return patients;
    }
}