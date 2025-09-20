package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// import org.json.JSONArray;
// import org.json.JSONObject;

// import persistence.JsonWritable;

// Manages all reservation requests
public class ReservationManager {
    private final List<Reservation> reservations;

    // REQUIRES: List<Reservation> must be initially empty
    // Constructs a list to store all reservation requests
    public ReservationManager() {
        this.reservations = new ArrayList<>();

    }

    public List<Reservation> getReservations() {
        return reservations;

    }

    // MODIFIES: this, telescope
    // EFFECTS: makes reservation, add to List<Reservation> -
    // and telescope.getAvailabilityStatus == false, return null if unavailable
    public Reservation makeReservation(String name, Telescope telescope) {
        if (!(telescope.getAvailabilityStatus())) {
            return null;
        }
        Reservation reservation = new Reservation(name, telescope);
        telescope.reserveTelescope();
        reservations.add(reservation);
        return reservation;
    }

    // MODIFIES: this, telescope
    // EFFECTS: cancels reservation with given id, marks telescope as available,
    // and removes from List<Reservation>, return false if otherwise
    public boolean cancelReservation(int id) {
        for (Reservation r : reservations) {
            if (r.getReservationId() == id) {
                r.cancelReservation();
                reservations.remove(r);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Converts Reservation to a JSON object.
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Reservation r : reservations) {
            jsonArray.put(r.toJson());
        }

        jsonObject.put("reservations", jsonArray);
        return jsonObject;
    }


}
