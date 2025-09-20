package persistence;

import model.Telescope;
import model.TelescopeCatalogue;
import model.ReservationManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// CREDIT TO CPSC210: JSONSERIALIZATIONDEMO FOR IMPLEMENTATION STYLE.

// Represents JSONreader that reads ReservationManager data from JSON file
// Dependent on loaded CatalogueReader data to handle changes made
public class ReservationJsonReader {
    private final String source;
    private final TelescopeCatalogue telescopeCatalogue;

    public ReservationJsonReader(String source, TelescopeCatalogue telescopeCatalogue) {
        this.source = source;
        this.telescopeCatalogue = telescopeCatalogue;
    }

    // EFFECTS: Reads reservation data from file and returns as ReservationManager.
    // Throw IOException if error occurs from reading the file.
    public ReservationManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseReservations(jsonObject);
    }

    // EFFECTS: Reads and converts JSONfile to string output
    // throw IOException if error occurs from reading file data
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(x -> contentBuilder.append(x));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Parses reservation data from JSON object and returns
    // ReservationManager.
    private ReservationManager parseReservations(JSONObject jsonObject) {
        ReservationManager reservationManager = new ReservationManager();
        JSONArray jsonArray = jsonObject.getJSONArray("reservations");

        for (Object obj : jsonArray) {
            JSONObject reservationJson = (JSONObject) obj;
            String user = reservationJson.getString("user");
            String telescopeName = reservationJson.getString("telescope");

            Telescope telescope = findTelescopeWithName(telescopeName);

            if (telescope != null) {
                reservationManager.makeReservation(user, telescope);
            } 
        }

        return reservationManager;
    }

    // EFFECTS: Return telescope with matching name in catalogue, null if otherwise
    private Telescope findTelescopeWithName(String telescopeName) {
        List<Telescope> catalogue = telescopeCatalogue.viewCatalogue();
        for (Telescope t : catalogue) {
            if (t.getTelescopeName().equalsIgnoreCase(telescopeName)) {
                return t;
            }
        }
        return null;
    }

}
