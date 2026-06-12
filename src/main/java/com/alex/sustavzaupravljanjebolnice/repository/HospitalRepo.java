package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Hospital repo.
 */
public class HospitalRepo implements Repository<Hospital, Long> {

    @Override
    public Hospital getById(Long aLong) throws SQLException {
        String query = "select * from hospital where id = ?";
        try (Connection conn = DatabaseManager.getConnection(); var ps = conn.prepareStatement(query)) {
            ps.setLong(1, aLong);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Hospital(rs.getLong("hospital_id"), rs.getString("name"), rs.getString("address"), rs.getString("phone_number"), rs.getString("email"));
                }
            }
        }
        return null;
    }

    @Override
    public List<Hospital> getAll() throws SQLException {
        String sql = "select * from hospital";
        try (Connection conn = DatabaseManager.getConnection()) {
            var ps = conn.prepareStatement(sql);
            try (var rs = ps.executeQuery()) {
                List<Hospital> hospitals = new ArrayList<>();
                while (rs.next()) {
                    hospitals.add(new Hospital(rs.getLong("hospital_id"), rs.getString("name"), rs.getString("address"), rs.getString("phone_number"), rs.getString("email")));
                }
                return hospitals;
            }
        }
    }

    @Override
    public void save(Hospital entity) throws SQLException {
        String sql = "insert into hospital (name, address, phone_number, email) values (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection(); var ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getAddress());
            ps.setString(3, entity.getPhoneNumber());
            ps.setString(4, entity.getEmail());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Hospital entity) throws SQLException {
        String sql = " update hospital set name = ?, address = ?, phone_number = ?, email = ? where id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getAddress());
            ps.setString(3, entity.getPhoneNumber());
            ps.setString(4, entity.getEmail());
            ps.setLong(5, entity.getId());
            ps.executeUpdate();

        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM hospital WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();

        }
    }
}
