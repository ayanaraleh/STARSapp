package model;

import org.json.JSONObject;

// Represents a telescope with its ID, name, type of build,
// and availability status

public class Telescope {
    private static int nextId = 100; // each scope gets unique id
    private int id; // id for inventory management
    private String name; // name of telescope brand
    private TelescopeType type; // specific type of build
    private String experienceLevel; // experience level based on its TelescopeType
    private boolean availabilityStatus; // whether or not scope is available to reserve

    // REQUIRES: availability status initially true (no reservations yet),
    // EFFECTS: constructs telescope with next available id, name, specified type
    // sets type experience level, and sets availability status to true
    public Telescope(String name, TelescopeType type) {
        this.id = nextId++;
        this.name = name;
        this.type = type;
        this.experienceLevel = type.getExperienceLevel();
        this.availabilityStatus = true;
    }

    public String getTelescopeName() {
        return name;

    }

    public int getTelescopeId() {
        return id;

    }

    public TelescopeType getTelescopeType() {
        return type;

    }

    public String getExperienceLevel() {
        return experienceLevel;

    }

    public boolean getAvailabilityStatus() {
        return availabilityStatus;

    }

    // MODIFIES: this
    // EFFECTS: marks Availability status as false when reserved
    public void reserveTelescope() {
        availabilityStatus = false;

    }

    // MODIFIES: this
    // EFFECTS: marks Availability status as true when returned
    public void returnTelescope() {
        availabilityStatus = true;

    }

    
    // EFFECTS: Converts telescope into a JSON object.

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", type.toString());
        return json;
    }

}
