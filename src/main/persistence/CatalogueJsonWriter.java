package persistence;

import model.TelescopeCatalogue;
import org.json.JSONObject;

import java.io.*;

// CREDIT TO CPSC210: JSONSERIALIZATIONDEMO FOR IMPLEMENTATION STYLE.

// Represents a JSONWriter: Writes Catalogue data to JSON file.
public class CatalogueJsonWriter {
    private final String filepath;
    private PrintWriter writer;
    private static final int INDENT = 4; // JSON indentation standard

    // EFFECTS: Constructs a writer to write to designated file path
    public CatalogueJsonWriter(String filepath) {
        this.filepath = filepath;
        // stub
    }

    // MODIFIES: this
    // EFFECTS: Open writer and throw FileNotFoundException if file cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(filepath));
    }

    // MODIFIES: this
    // EFFECTS: Converts TelescopeCatalogue to JSON string and saves to file
    public void write(TelescopeCatalogue catalogue) {
        JSONObject jsonObject = catalogue.toJson();
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
