package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Department;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Department repo.
 */
public class DepartmentRepo implements Repository<Department, Long> {

    @Override
    public Department getById(Long id) throws SQLException {
        String query = "SELECT * FROM department WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDepartment(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                departments.add(mapDepartment(rs));
            }
        }
        return departments;
    }

    @Override
    public void save(Department entity) throws SQLException {
        String sql = "INSERT INTO department (name, hospital_id) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getName());
            ps.setLong(2, entity.getHospital().getId());

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
    public void update(Department entity) throws SQLException {
        String sql = "UPDATE department SET name = ?, hospital_id = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getName());
            ps.setLong(2, entity.getHospital().getId());
            ps.setLong(3, entity.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM department WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Department mapDepartment(ResultSet rs) throws SQLException {
        Department department = new Department(rs.getLong("id"), rs.getString("name"), null);
        department.setId(rs.getLong("id"));
        department.setName(rs.getString("name"));

        Hospital hospital = new Hospital();
        hospital.setId(rs.getLong("hospital_id"));
        department.setHospital(hospital);

        return department;
    }
}