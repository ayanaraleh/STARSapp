package model;

import org.json.JSONObject;

// Represents a reservation made for a telescope
public class Reservation {
    private static int nextResoId = 1; // static counter for unique reso id
    private final int reservationId;
    private final String username;
    private final Telescope telescope;
    private boolean reservationStatus;

    // Constructs a reservation with an automatically assigned id,
    // name of reserver, requested telescope, and if its active or cancelled
    public Reservation(String username, Telescope telescope) {
        this.reservationId = nextResoId++;
        this.username = username;
        this.telescope = telescope;
        this.reservationStatus = true; // default status is true

    }

    public int getReservationId() {
        return reservationId; // stub

    }

    public String getUserName() {
        return username; // stub

    }

    public Telescope getTelescope() {
        return telescope; // stub
    }

    public boolean getReservationStatus() {
        return reservationStatus; // stub
    }

    // MODIFIES: this, telescope
    // EFFECTS: sets status to false indicating reservation is
    // inactive, changes telescope availability to available

    public void cancelReservation() {
        if (reservationStatus) {
            reservationStatus = false;
            telescope.returnTelescope();
        }

    }

    // EFFECTS: Converts reservation into JSON object.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("user", username);
        json.put("telescope", telescope.getTelescopeName()); 
        return json;
    }

}
