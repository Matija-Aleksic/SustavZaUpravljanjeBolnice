package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.builders.AdministratorBuilder;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Administrator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepo implements Repository<Administrator, Integer> {

    @Override
    public Administrator getById(Integer id) throws SQLException {
        String sql = "SELECT id, first_name, last_name, oib, birth_date, role, permissions, email, salary, phone_number, address, hospital_id FROM STAFF WHERE id = ? AND role = 'ADMIN'";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AdministratorBuilder builder = new AdministratorBuilder();
                    builder.setId(rs.getInt("id"));
                    builder.setFirstName(rs.getString("first_name"));
                    builder.setLastName(rs.getString("last_name"));
                    builder.setOib(rs.getString("oib"));
                    builder.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    builder.setEmail(rs.getString("email"));
                    builder.setSalary(rs.getDouble("salary"));
                    builder.setRole(StaffRoles.ADMIN);
                    Hospital hospital = new Hospital();
                    hospital.setId(rs.getLong("hospital_id"));
                    builder.setHospital(hospital);

                    conn.commit();
                    return builder.build();
                }
            }
            conn.commit();
            return null;
        }
    }

    @Override
    public List<Administrator> getAll() throws SQLException {
        String sql = "SELECT id, first_name, last_name, oib, birth_date, role, permissions, email, salary, phone_number, address, hospital_id FROM STAFF WHERE role = 'ADMIN'";

        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {

            List<Administrator> administrators = new ArrayList<>();

            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    long adminId = rs.getLong("id");
                    AdministratorBuilder builder = new AdministratorBuilder();
                    builder.setId((int) adminId);
                    builder.setFirstName(rs.getString("first_name"));
                    builder.setLastName(rs.getString("last_name"));
                    builder.setOib(rs.getString("oib"));
                    builder.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    builder.setEmail(rs.getString("email"));
                    builder.setSalary(rs.getDouble("salary"));
                    builder.setRole(StaffRoles.ADMIN);
                    Hospital hospital = new Hospital();
                    hospital.setId(rs.getLong("hospital_id"));
                    builder.setHospital(hospital);

                    administrators.add(builder.build());
                }
            }

            conn.commit();
            return administrators;
        }
    }

    @Override
    public void save(Administrator entity) throws SQLException {
        String sql = "insert into STAFF (first_name, last_name, oib, birth_date, email, salary, ROLE, hospital_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getOib());
            ps.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));
            ps.setString(5, entity.getEmail());
            ps.setDouble(6, entity.getSalary());
            ps.setString(7, String.valueOf(entity.getRole()));
            if (entity.getHospital() != null) {
                ps.setLong(8, entity.getHospital().getId());
            } else {
                ps.setNull(8, Types.BIGINT);
            }
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Administrator entity) throws SQLException {
        String sql = "update STAFF set first_name = ?, last_name = ?, oib = ?, birth_date = ?, email = ?, salary = ?, hospital_id = ? where id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getOib());
            ps.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));
            ps.setString(5, entity.getEmail());
            ps.setDouble(6, entity.getSalary());
            if (entity.getHospital() != null) {
                ps.setLong(7, entity.getHospital().getId());
            } else {
                ps.setNull(7, Types.BIGINT);
            }
            ps.setLong(8, entity.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = "delete from STAFF where id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}