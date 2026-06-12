package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.repository.AppointmentRepo;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.HospitalRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PatientRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Entity hydrator util.
 */
public class EntityHydratorUtil {

    private static final DoctorRepo doctorRepo = new DoctorRepo();
    private static final PatientRepo patientRepo = new PatientRepo();
    private static final AppointmentRepo appointmentRepo = new AppointmentRepo();
    private static final HospitalRepo hospitalRepo = new HospitalRepo();


    private EntityHydratorUtil() {
        //for sonarqube
    }

    /**
     * Gets full doctor.
     *
     * @param doctorId the doctor id
     * @return the full doctor
     * @throws SQLException the sql exception
     */
    public static Doctor getFullDoctor(Long doctorId) throws SQLException {
        Doctor doctor = doctorRepo.getById(doctorId);
        if (doctor == null) return null;


        if (doctor.getHospital() != null && doctor.getHospital().getId() != null) {
            Hospital fullHospital = hospitalRepo.getById(doctor.getHospital().getId());
            doctor.setHospital(fullHospital);
        }


        doctor.setAssignedPatients(fetchAssignedPatientsForDoctor(doctorId));
        doctor.setAppointments(fetchAppointmentsForDoctor(doctorId));

        return doctor;
    }

    /**
     * Gets full patient.
     *
     * @param patientId the patient id
     * @return the full patient
     * @throws SQLException the sql exception
     */
    public static Patient getFullPatient(Long patientId) throws SQLException {
        Patient patient = patientRepo.getById(patientId);
        if (patient == null) return null;

        if (patient.getAssignedDoctor() != null && patient.getAssignedDoctor().getId() != null) {
            Doctor fullDoctor = doctorRepo.getById(Long.valueOf(patient.getAssignedDoctor().getId()));
            patient.setAssignedDoctor(fullDoctor);
        }


        if (patient.getHospital() != null && patient.getHospital().getId() != null) {
            Hospital fullHospital = hospitalRepo.getById(patient.getHospital().getId());
            patient.setHospital(fullHospital);
        }

        return patient;
    }

    private static List<Patient> fetchAssignedPatientsForDoctor(Long doctorId) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT id FROM patient WHERE assigned_doctor_id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    patients.add(patientRepo.getById(rs.getLong("id")));
                }
            }
        }
        return patients;
    }

    private static List<Appointment> fetchAppointmentsForDoctor(Long doctorId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT id FROM appointment WHERE doctor_id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(appointmentRepo.getById(rs.getLong("id")));
                }
            }
        }
        return appointments;
    }
}