package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.builders.AdministratorBuilder;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Administrator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdminRepo implements Repository<Administrator, Integer> {

    @Override
    public Administrator getById(Integer id) throws SQLException {
        String sql = "SELECT id, first_name, last_name, oib, birth_date, role, permissions, email, salary, phone_number, address, HOSPITAL_ID  FROM STAFF WHERE id = ? AND role = 'ADMIN'";

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
                    long nurseId = rs.getLong("id");
                    AdministratorBuilder builder = new AdministratorBuilder();
                    builder.setId((int) nurseId);
                    builder.setFirstName(rs.getString("first_name"));
                    builder.setLastName(rs.getString("last_name"));
                    builder.setOib(rs.getString("oib"));
                    builder.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    builder.setEmail(rs.getString("email"));
                    builder.setSalary(rs.getDouble("salary"));
                    builder.setRole(StaffRoles.ADMIN);
                    builder.setHospitalId(rs.getLong("hospital_id"));

                    administrators.add(builder.build());
                }
            }

            conn.commit();
            return administrators;
        }
    }

    @Override
    public void save(Administrator entity) throws SQLException {
        String sql = "insert into STAFF (first_name, last_name, oib, birth_date, email, salary,ROLE) values (?, ?, ?, ?, ?, ?,?)";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getOib());
            ps.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));
            ps.setString(5, entity.getEmail());
            ps.setDouble(6, entity.getSalary());
            ps.setString(7, String.valueOf(entity.getRole()));
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Administrator entity) throws SQLException {
        String sql = "update STAFF set first_name = ?, last_name = ?, oib = ?, birth_date = ?, email = ?, salary = ? where id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getOib());
            ps.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));
            ps.setString(5, entity.getEmail());
            ps.setDouble(6, entity.getSalary());
            ps.setLong(7, entity.getId());
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
