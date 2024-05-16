package service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;



public class AuditService {
    private static final String CSV_FILE_PATH = "audit.csv";
    private static final String CSV_HEADER = "nume_actiune, timestamp";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static AuditService instance;
    private PrintWriter writer;

    public AuditService() {
        try {
            writer = new PrintWriter(new FileWriter(CSV_FILE_PATH, true));
            if (writer.checkError()) {
                writer.println(CSV_HEADER);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String actionName) {
        String timestamp = DATE_FORMAT.format(new Date());
        String logEntry = actionName;
        writer.println(logEntry);
        writer.flush();
    }

    public void close() {
        if (writer != null) {
            //writer.close();
        }
    }
    
}
