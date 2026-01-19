package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type JSON file manager.
 */
public class JsonFileManager {
    private static final Logger logger = LoggerFactory.getLogger(JsonFileManager.class);
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

    private JsonFileManager() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Save to JSON.
     *
     * @param <T>      the type parameter
     * @param filePath the file path
     * @param data     the data
     */
    public static <T> void saveToJson(String filePath, List<T> data) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
            logger.info("Data saved to JSON file: {}", filePath);
        } catch (IOException e) {
            logger.error("Error saving to JSON file: {}", filePath, e);
        }
    }

    /**
     * Load from JSON list.
     *
     * @param <T>      the type parameter
     * @param filePath the file path
     * @param type     the type
     * @return the list
     */
    public static <T> List<T> loadFromJson(String filePath, Type type) {
        try {
            if (!Files.exists(Paths.get(filePath))) {
                logger.warn("JSON file does not exist: {}", filePath);
                return new ArrayList<>();
            }

            try (Reader reader = new FileReader(filePath)) {
                List<T> result = gson.fromJson(reader, type);
                logger.info("Data loaded from JSON file: {}", filePath);
                return result != null ? result : new ArrayList<>();
            }
        } catch (IOException e) {
            logger.error("Error loading from JSON file: {}", filePath, e);
            return new ArrayList<>();
        }
    }

    /**
     * Gets gson.
     *
     * @return the gson
     */
    public static Gson getGson() {
        return gson;
    }
}
