package io.tyze.b.fun7service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Helper {
	public static boolean isValidJSONObject(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidJSONArray(String test) {
		try {
			new JSONArray(test);
		} catch (JSONException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidJSON(String test) {
		if (!isValidJSONObject(test)) {
			return isValidJSONArray(test);
		}
		return true;
	}
	
	
}
