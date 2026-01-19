package entity.repository.file;

import entity.Hospital;
import com.google.gson.reflect.TypeToken;
import entity.repository.HospitalRepository;
import util.JsonFileManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HospitalFileRepository implements HospitalRepository {
    private static final String FILE_PATH = "src/main/java/file/hospitals.json";
    private static final Type LIST_TYPE = new TypeToken<ArrayList<Hospital>>(){}.getType();

    @Override
    public List<Hospital> findAll() {
        List<Hospital> hospitals = JsonFileManager.loadFromJson(FILE_PATH, LIST_TYPE);
        return hospitals != null ? hospitals : new ArrayList<>();
    }

    @Override
    public Optional<Hospital> findById(Integer id) {
        return Optional.ofNullable(findAll().stream()
                .filter(h -> h.getId() == id)
                .findFirst()
                .orElseThrow(() -> new exception.EntityNotFoundException("Hospital not found with id: " + id)));
    }

    @Override
    public void save(Hospital entity) {
        List<Hospital> hospitals = findAll();
        hospitals.removeIf(h -> h.getId() == entity.getId());
        hospitals.add(entity);
        saveAll(hospitals);
    }

    @Override
    public void saveAll(List<Hospital> entities) {
        JsonFileManager.saveToJson(FILE_PATH, entities);
    }

    @Override
    public void deleteById(Integer id) {
        List<Hospital> hospitals = findAll();
        hospitals.removeIf(h -> h.getId() == id);
        saveAll(hospitals);
    }

    @Override
    public void deleteAll() {
        saveAll(new ArrayList<>());
    }
}
