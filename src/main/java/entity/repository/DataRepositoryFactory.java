package entity.repository;

public interface DataRepositoryFactory {
    HospitalRepository getHospitalRepository();
    DoctorRepository getDoctorRepository();
    PatientRepository getPatientRepository();
    AppointmentRepository getAppointmentRepository();
}
