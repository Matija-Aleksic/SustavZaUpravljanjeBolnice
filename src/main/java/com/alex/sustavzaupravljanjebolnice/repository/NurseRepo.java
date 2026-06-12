package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.Nurse;
import com.alex.sustavzaupravljanjebolnice.entity.NurseBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NurseRepo implements Repository<Nurse, Long> {

    @Override
    public Nurse getById(Long aLong) throws SQLException {
        String sql = "select * from nurse where id = ?";
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, aLong);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                NurseBuilder builder = new NurseBuilder();
                builder.setId(rs.getInt("id"));
                builder.setFirstName(rs.getString("first_name"));
                builder.setLastName(rs.getString("last_name"));
                builder.setOib(rs.getString("oib"));
                builder.setBirthDate(rs.getDate("birth_date").toLocalDate());
                builder.setEmail(rs.getString("email"));
                builder.setSalary(rs.getDouble("salary"));
                return builder.build();
            }
            return null;
        }
    }

    @Override
    public List<Nurse> getAll() throws SQLException {
        String sql = "select * from nurse";
        try (Connection conn = DatabaseManager.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Nurse> nurses = new ArrayList<>();
            while (rs.next()) {
                NurseBuilder builder = new NurseBuilder();
                builder.setId(rs.getInt("id"));
                builder.setFirstName(rs.getString("first_name"));
                builder.setLastName(rs.getString("last_name"));
                builder.setOib(rs.getString("oib"));
                builder.setBirthDate(rs.getDate("birth_date").toLocalDate());
                builder.setEmail(rs.getString("email"));
                builder.setSalary(rs.getDouble("salary"));
                nurses.add(builder.build());
            }
            return nurses;

        }
    }

    @Override
    public void save(Nurse entity) throws SQLException {
        String sql = "insert into nurse (first_name, last_name, oib, birth_date, email, salary) values (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getOib());
            ps.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));
            ps.setString(5, entity.getEmail());
            ps.setDouble(6, entity.getSalary());
            ps.executeUpdate();
        }

    }

    @Override
    public void update(Nurse entity) throws SQLException {
        String sql = "update nurse set first_name = ?, last_name = ?, oib = ?, birth_date = ?, email = ?, salary = ? where id = ?";
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
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
    public void deleteById(Long aLong) throws SQLException {
        String sql = "delete from nurse where id = ?";
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, aLong);
            ps.executeUpdate();
        }

    }
}
