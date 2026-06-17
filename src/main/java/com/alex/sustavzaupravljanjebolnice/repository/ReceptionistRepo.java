package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.Receptionist;
import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Receptionist repo.
 */
public class ReceptionistRepo implements Repository<Receptionist, Long> {

    @Override
    public Receptionist getById(Long id) throws SQLException {
        String query = "SELECT id, first_name, last_name, oib, birth_date, permissions, email, salary, phone_number, address, hospital_id " +
                "FROM staff WHERE id = ? AND role = 'RECEPTIONIST'";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapReceptionist(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Receptionist> getAll() throws SQLException {
        List<Receptionist> receptionists = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, oib, birth_date, permissions, email, salary, phone_number, address, hospital_id " +
                "FROM staff WHERE role = 'RECEPTIONIST'";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                receptionists.add(mapReceptionist(rs));
            }
        }
        return receptionists;
    }

    @Override
    public void save(Receptionist entity) throws SQLException {
        String sql = "INSERT INTO staff (first_name, last_name, oib, birth_date, role, permissions, email, salary, phone_number, address, hospital_id) " +
                "VALUES (?, ?, ?, ?, 'RECEPTIONIST', ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getOib());
            ps.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));

            ps.setString(6, entity.getEmail());
            ps.setDouble(7, entity.getSalary());


            if (entity.getHospital() != null && entity.getHospital().getId() != null) {
                ps.setLong(10, entity.getHospital().getId());
            } else {
                ps.setNull(10, java.sql.Types.BIGINT);
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
    public void update(Receptionist entity) throws SQLException {
        String sql = "UPDATE staff SET first_name = ?, last_name = ?, oib = ?, birth_date = ?, permissions = ?, email = ?, salary = ?, phone_number = ?, address = ?, hospital_id =? " +
                "WHERE id = ? AND role = 'RECEPTIONIST'";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getOib());
            ps.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));

            ps.setString(6, entity.getEmail());
            ps.setDouble(7, entity.getSalary());


            if (entity.getHospital() != null && entity.getHospital().getId() != null) {
                ps.setLong(10, entity.getHospital().getId());
            } else {
                ps.setNull(10, java.sql.Types.BIGINT);
            }

            ps.setLong(11, entity.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM staff WHERE id = ? AND role = 'RECEPTIONIST'";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Receptionist mapReceptionist(ResultSet rs) throws SQLException {
        Receptionist receptionist = new Receptionist();
        receptionist.setId(rs.getLong("id"));
        receptionist.setFirstName(rs.getString("first_name"));
        receptionist.setLastName(rs.getString("last_name"));
        receptionist.setOib(rs.getString("oib"));
        receptionist.setBirthDate(rs.getDate("birth_date").toLocalDate());
        receptionist.setRole(StaffRoles.RECEPTIONIST);
        receptionist.setEmail(rs.getString("email"));
        receptionist.setSalary(rs.getDouble("salary"));


        long hospitalId = rs.getLong("hospital_id");
        if (!rs.wasNull()) {
            Hospital hospital = new Hospital();
            hospital.setId(hospitalId);
            receptionist.setHospital(hospital);
        }

        return receptionist;
    }
}