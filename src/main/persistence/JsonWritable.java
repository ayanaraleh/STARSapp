package persistence;

import org.json.JSONObject;

// CREDIT TO CPSC210: JSONSERIALIZATIONDEMO FOR PERSISTENCE STYLE.

// Interface for objects of type X to be converted to JSON
// EFFECTS: convert object of type X to JSON object
public interface JsonWritable<X> {
    JSONObject toJson(X object);
}
