package persistence;

import model.ReservationManager;
import org.json.JSONObject;

import java.io.*;

public class ReservationJsonWriter {
    private final String filepath;
    private PrintWriter writer;
    private static final int INDENT = 4; // JSON indentation standard

    
    // EFFECTS: Constructs a writer to write to designated file path
    public ReservationJsonWriter(String filepath) {
        this.filepath = filepath;
    
    }

    // MODIFIES: this
    // EFFECTS: Open writer and throw FileNotFoundException if file cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(filepath));
    }

    // MODIFIES: this
    // EFFECTS: Converts ReservationManager to JSON string and saves to file
    public void write(ReservationManager reservationManager) {
        JSONObject jsonObject = reservationManager.toJson();
        saveToFile(jsonObject.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: Closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: Saves JSON string to file path
    public void saveToFile(String json) {
        writer.print(json);
    }
}
