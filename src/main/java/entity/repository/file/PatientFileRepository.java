package entity.repository.file;

import entity.Patient;
import com.google.gson.reflect.TypeToken;
import entity.repository.PatientRepository;
import util.JsonFileManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientFileRepository implements PatientRepository {
    private static final String FILE_PATH = "src/main/java/file/patients.json";
    private static final Type LIST_TYPE = new TypeToken<ArrayList<Patient>>(){}.getType();

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = JsonFileManager.loadFromJson(FILE_PATH, LIST_TYPE);
        return patients != null ? patients : new ArrayList<>();
    }

    @Override
    public Optional<Patient> findById(Integer id) {
        return findAll().stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public void save(Patient entity) {
        List<Patient> patients = findAll();
        patients.removeIf(p -> p.getId() == entity.getId());
        patients.add(entity);
        saveAll(patients);
    }

    @Override
    public void saveAll(List<Patient> entities) {
        JsonFileManager.saveToJson(FILE_PATH, entities);
    }

    @Override
    public void deleteById(Integer id) {
        List<Patient> patients = findAll();
        patients.removeIf(p -> p.getId() == id);
        saveAll(patients);
    }

    @Override
    public void deleteAll() {
        saveAll(new ArrayList<>());
    }
}
