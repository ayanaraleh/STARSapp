package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.JsonWritable;

// Represents a catalogue of telescopes available for rental
public class TelescopeCatalogue {
    private List<Telescope> telescopes;

    // EFFECTS: constructs an empty catalogue of telescopes
    public TelescopeCatalogue() {
        this.telescopes = new ArrayList<>();

    }

    // EFFECTS: returns list of all telescopes in catalogue,
    // both available and unavailable
    public List<Telescope> viewCatalogue() {
        return telescopes;
    }

    // MODIFIES: this
    // EFFECTS: adds a new telescope to the catalogue
    public void addTelescope(Telescope t) {
        telescopes.add(t);
    }

    // EFFECTS: returns telescope with given id,
    // null if no matching id is found
    public Telescope getTelescope(int id) {
        for (Telescope t : telescopes) {
            if (t.getTelescopeId() == id) {
                return t;
            }
        }
        return null;

    }

    // REQUIRES: catalogue must have a telescope with matching
    // id as telescope to be removed and must be available.
    // MODIFIES: this
    // EFFECTS: removes given telescope from catalogue and returns true
    // indicating success, false if otherwise

    public boolean removeTelescope(int id) {
        for (Telescope t : telescopes) {
            if (t.getTelescopeId() == id && t.getAvailabilityStatus()) {
                telescopes.remove(t);
                return true;
            }
        }
        return false;

    }

    // EFFECTS: returns list of available telescopes
    public List<Telescope> getAvailableTelescopes() {
        List<Telescope> availableTelescopes = new ArrayList<>();

        for (Telescope t : telescopes) {
            if (t.getAvailabilityStatus()) {
                availableTelescopes.add(t);

            }
        }
        return availableTelescopes;

    }

    // EFFECTS: returns list of unavailable telescopes
    public List<Telescope> getUnavailableTelescopes() {
        List<Telescope> unavailableTelescopes = new ArrayList<>();

        for (Telescope t : telescopes) {
            if (!(t.getAvailabilityStatus())) {
                unavailableTelescopes.add(t);

            }
        }
        return unavailableTelescopes;

    }

    // REQUIRES: experienceLevel inputted as uppercase
    // EFFECTS: returns list of available telescopes filtered
    // based on indicated experienceLevel;
    public List<Telescope> filterByExperience(String experienceLevel) {
        List<Telescope> filteredExperience = new ArrayList<>();

        for (Telescope t : telescopes) {
            if (t.getExperienceLevel() == experienceLevel && t.getAvailabilityStatus()) {
                filteredExperience.add(t);

            }
        }
        return filteredExperience;

    }

    // EFFECTS: Convert and return catalogue data to JSON type
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Telescope t : telescopes) {
            jsonArray.put(t.toJson());
        }

        jsonObject.put("telescopes", jsonArray);
        return jsonObject;
    }

}