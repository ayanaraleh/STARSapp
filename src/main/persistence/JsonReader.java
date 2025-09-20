package persistence;

import org.json.JSONObject;

// CREDIT TO CPSC210: JSONSERIALIZATIONDEMO FOR PERSISTENCE STYLE.

// Interface for JSON objects to be converted as objects of type X 
// EFFECTS: Parse JSON object and convert to instance of X
public interface JsonReader<X> {
    X fromJson(JSONObject jsonObject);
}
