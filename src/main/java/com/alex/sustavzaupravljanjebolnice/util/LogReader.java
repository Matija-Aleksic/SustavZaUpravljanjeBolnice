package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.entity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Log reader.
 */
public class LogReader {

    /**
     * The constant FILE_PATH.
     */
    protected static final String FILE_PATH = "logs/changes.dat";
    private static final Logger log = LoggerFactory.getLogger(LogReader.class);

    private LogReader() {
    }


    /**
     * Read logs from file list.
     *
     * @return the list
     */
    public static synchronized List<Activity> readLogsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Activity>) inputStream.readObject();
        } catch (Exception e) {
            log.error("Warning: Could not deserialize audit logs safely: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}