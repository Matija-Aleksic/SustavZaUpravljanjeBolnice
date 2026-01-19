package entity.repository.file;

import entity.Doctor;
import com.google.gson.reflect.TypeToken;
import entity.repository.DoctorRepository;
import util.JsonFileManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorFileRepository implements DoctorRepository {
    private static final String FILE_PATH = "src/main/java/file/doctors.json";
    private static final Type LIST_TYPE = new TypeToken<ArrayList<Doctor>>(){}.getType();

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = JsonFileManager.loadFromJson(FILE_PATH, LIST_TYPE);
        return doctors != null ? doctors : new ArrayList<>();
    }

    @Override
    public Optional<Doctor> findById(Integer id) {
        return findAll().stream().filter(d -> d.getId() == id).findFirst();
    }

    @Override
    public void save(Doctor entity) {
        List<Doctor> doctors = findAll();
        doctors.removeIf(d -> d.getId() == entity.getId());
        doctors.add(entity);
        saveAll(doctors);
    }

    @Override
    public void saveAll(List<Doctor> entities) {
        JsonFileManager.saveToJson(FILE_PATH, entities);
    }

    @Override
    public void deleteById(Integer id) {
        List<Doctor> doctors = findAll();
        doctors.removeIf(d -> d.getId() == id);
        saveAll(doctors);
    }

    @Override
    public void deleteAll() {
        saveAll(new ArrayList<>());
    }
}
