package utils;

import config.ConfigReader;

import java.util.HashMap;

public class Headers {
    public static HashMap<String,Object> headers = new HashMap<>();
    public static String authToken = ConfigReader.get("authToken");
    public static HashMap<String,Object> InspectionApiHeader()
    {
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        headers.put("X_VEHICLE_TYPE","CAR");
        headers.put("Authorization",authToken);
        headers.put("X_COUNTRY","IN");

        return headers;
    }
}
