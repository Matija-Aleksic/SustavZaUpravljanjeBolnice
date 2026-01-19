package entity.repository.file;

import entity.repository.*;


public class FileRepositoryFactory implements DataRepositoryFactory {
    @Override
    public HospitalRepository getHospitalRepository() {
        return new HospitalFileRepository();
    }
    @Override
    public DoctorRepository getDoctorRepository() {
        return new DoctorFileRepository();
    }
    @Override
    public PatientRepository getPatientRepository() {
        return new PatientFileRepository();
    }
    @Override
    public AppointmentRepository getAppointmentRepository() {
        return new AppointmentFileRepository();
    }
}
