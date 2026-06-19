package com.alex.sustavzaupravljanjebolnice.util;


import com.alex.sustavzaupravljanjebolnice.entity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class LogWriter {

    private static final Logger log = LoggerFactory.getLogger(LogWriter.class);

    private LogWriter() {
    }

    public static void writeLogAsync(Activity newActivity) {
        if (newActivity == null) return;

        Thread.startVirtualThread(() -> performFileWrite(newActivity));
    }

    private static synchronized void performFileWrite(Activity newActivity) {
        File directory = new File("logs");
        if (!directory.exists()) {
            directory.mkdir();
        }

        List<Activity> currentLogs = LogReader.readLogsFromFile();
        currentLogs.add(newActivity);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(LogReader.FILE_PATH))) {
            outputStream.writeObject(currentLogs);
        } catch (IOException e) {
            log.error("Critical: Failed to append to binary log framework.");
            e.printStackTrace();
        }
    }
}