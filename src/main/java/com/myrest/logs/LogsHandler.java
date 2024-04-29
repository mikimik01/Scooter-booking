package com.myrest.logs;

import com.myrest.exceptions.CustomException;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogsHandler {
    private static FileWriter writer;
    private final static String fileName = "logs/logs.txt";

    private LogsHandler(){}
    private static void open() {
        try {
            writer = new FileWriter(fileName, true);
        } catch (IOException e) {
            System.out.println("Problem with opening " + fileName);
        }
    }

    public static void log(String message) {
        open();
        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write(timeStamp + " - " + message + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Problem with saving to " + fileName);
        }
        close();
    }

    private static void close() {
        try {
            if (writer != null) {
                writer.close(); // Zamknięcie FileWritera po zakończeniu pracy.
            }
        } catch (IOException e) {
            System.out.println("Problem with closing " + fileName);
        }
    }
}
