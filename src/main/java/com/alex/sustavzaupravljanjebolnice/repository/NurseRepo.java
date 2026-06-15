package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.Nurse;
import com.alex.sustavzaupravljanjebolnice.entity.NurseBuilder;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Nurse repo.
 */
public class NurseRepo implements Repository<Nurse, Long> {
    private final WardRepo wardRepo = new WardRepo();
    private final HospitalRepo hospitalRepo = new HospitalRepo();

    @Override
    public Nurse getById(Long id) throws SQLException {
        String sql = "SELECT id, first_name, last_name, oib, birth_date, role, permissions, email, salary, phone_number, address, hospital_id FROM STAFF WHERE id = ? AND role = 'NURSE'";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    NurseBuilder builder = new NurseBuilder();
                    builder.setId(rs.getInt("id"));
                    builder.setFirstName(rs.getString("first_name"));
                    builder.setLastName(rs.getString("last_name"));
                    builder.setOib(rs.getString("oib"));
                    builder.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    builder.setEmail(rs.getString("email"));
                    builder.setSalary(rs.getDouble("salary"));

                    List<Ward> associatedWards = wardRepo.getAll().stream().filter(w -> w.getNurseId() != null && w.getNurseId().longValue() == id).toList();
                    builder.setWards(associatedWards);

                    conn.commit();
                    return builder.build();
                }
            }
            conn.commit();
            return null;
        }
    }

    @Override
    public List<Nurse> getAll() throws SQLException {
        String sql = "SELECT id, first_name, last_name, oib, birth_date, role, permissions, email, salary, phone_number, address, hospital_id FROM STAFF WHERE role = 'NURSE'";

        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
            List<Hospital> allHospitals = hospitalRepo.getAll();
            List<Ward> allWards = wardRepo.getAll();
            List<Nurse> nurses = new ArrayList<>();

            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    long nurseId = rs.getLong("id");
                    NurseBuilder builder = new NurseBuilder();
                    builder.setId((int) nurseId);
                    builder.setFirstName(rs.getString("first_name"));
                    builder.setLastName(rs.getString("last_name"));
                    builder.setOib(rs.getString("oib"));
                    builder.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    builder.setEmail(rs.getString("email"));
                    builder.setSalary(rs.getDouble("salary"));
                    long hospitalId = rs.getLong("hospital_id");
                    Hospital hospital = allHospitals.stream().filter(h -> h.getId() == hospitalId).findFirst().orElse(null);
                    List<Ward> assignedWards = allWards.stream().filter(w -> w.getNurseId() != null && w.getNurseId() == nurseId).toList();

                    builder.setWards(assignedWards);
                    builder.setHospital(hospital);
                    nurses.add(builder.build());
                }
            }

            conn.commit();
            return nurses;
        }
    }

    @Override
    public void save(Nurse entity) throws SQLException {
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
    public void update(Nurse entity) throws SQLException {
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
    public void deleteById(Long aLong) throws SQLException {
        String sql = "delete from STAFF where id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, aLong);
            ps.executeUpdate();
        }

    }
}
