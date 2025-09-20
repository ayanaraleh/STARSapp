package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import model.Telescope;
import model.TelescopeCatalogue;
import model.TelescopeType;

// CREDIT TO CPSC210: JSONSERIALIZATIONDEMO FOR IMPLEMENTATION STYLE.

// Represents JSONreader that reads TelescopeCatalogue from JSON file
public class CatalogueJsonReader {
    private final String filepath;

    public CatalogueJsonReader(String filepath) {
        this.filepath = filepath;
    }

    
// EFFECTS: Reads telescope catalogue from file and returns as a list.
// Throw IOException if error occurs from reading the file.
    public TelescopeCatalogue read() throws IOException {
        String jsonData = readFile(filepath);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCatalogue(jsonObject);
    }

    // EFFECTS: Reads and converts JSONfile to string output
    // throw IOException if error occurs from reading file data
    private String readFile(String filepath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filepath), StandardCharsets.UTF_8)) {
            stream.forEach(x -> contentBuilder.append(x));
        }

        return contentBuilder.toString();
    }

    
    // EFFECTS: Parses telescope data from JSON object and returns a list.
    private TelescopeCatalogue parseCatalogue(JSONObject jsonObject) {
        TelescopeCatalogue telescopeCatalogue = new TelescopeCatalogue();
        JSONArray jsonArray = jsonObject.getJSONArray("telescopes");

        for (Object obj : jsonArray) {
            JSONObject telescopeJson = (JSONObject) obj;
            Telescope telescope = new Telescope(
                    telescopeJson.getString("name"),
                    TelescopeType.valueOf(telescopeJson.getString("type"))
            );
            telescopeCatalogue.addTelescope(telescope);
        }

        return telescopeCatalogue;
    }
}
