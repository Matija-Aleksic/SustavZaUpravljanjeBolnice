package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Department;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WardRepo implements Repository<Ward, Long> {

    @Override
    public Ward getById(Long id) throws SQLException {
        String query = "SELECT * FROM ward WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapWard(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Ward> getAll() throws SQLException {
        List<Ward> wards = new ArrayList<>();
        String sql = "SELECT * FROM ward";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                wards.add(mapWard(rs));
            }
        }
        return wards;
    }

    @Override
    public void save(Ward entity) throws SQLException {
        String sql = "INSERT INTO ward (name, max_capacity, capacity, department_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getMaxCapacity());
            ps.setInt(3, entity.getCapacity());
            ps.setLong(4, entity.getDepartment().getId());

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
        String sql = "UPDATE ward SET name = ?, max_capacity = ?, capacity = ?, department_id = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getMaxCapacity());
            ps.setInt(3, entity.getCapacity());
            ps.setLong(4, entity.getDepartment().getId());
            ps.setLong(5, entity.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM ward WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Ward mapWard(ResultSet rs) throws SQLException {
        Ward ward = new Ward();
        ward.setId(rs.getLong("id"));
        ward.setName(rs.getString("name"));
        ward.setMaxCapacity(rs.getInt("max_capacity"));
        ward.setCapacity(rs.getInt("capacity"));

        Department department = new Department();
        department.setId(rs.getLong("department_id"));
        ward.setDepartment(department);

        return ward;
    }
}