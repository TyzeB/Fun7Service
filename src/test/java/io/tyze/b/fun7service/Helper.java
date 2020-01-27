package io.tyze.b.fun7service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Helper {
	public static boolean isValidJSON(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}
}
