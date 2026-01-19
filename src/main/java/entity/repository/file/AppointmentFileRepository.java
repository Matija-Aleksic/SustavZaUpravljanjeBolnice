package entity.repository.file;

import entity.Appointment;
import com.google.gson.reflect.TypeToken;
import entity.repository.AppointmentRepository;
import util.JsonFileManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppointmentFileRepository implements AppointmentRepository {
    private static final String FILE_PATH = "src/main/java/file/appointments.json";
    private static final Type LIST_TYPE = new TypeToken<ArrayList<Appointment>>(){}.getType();

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = JsonFileManager.loadFromJson(FILE_PATH, LIST_TYPE);
        return appointments != null ? appointments : new ArrayList<>();
    }

    @Override
    public Optional<Appointment> findById(Integer id) {
        return findAll().stream().filter(a -> a.getId() == id).findFirst();
    }

    @Override
    public void save(Appointment entity) {
        List<Appointment> appointments = findAll();
        appointments.removeIf(a -> a.getId() == entity.getId());
        appointments.add(entity);
        saveAll(appointments);
    }

    @Override
    public void saveAll(List<Appointment> entities) {
        JsonFileManager.saveToJson(FILE_PATH, entities);
    }

    @Override
    public void deleteById(Integer id) {
        List<Appointment> appointments = findAll();
        appointments.removeIf(a -> a.getId() == id);
        saveAll(appointments);
    }

    @Override
    public void deleteAll() {
        saveAll(new ArrayList<>());
    }
}
