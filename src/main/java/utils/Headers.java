package utils;

import config.ConfigReader;

import java.util.HashMap;

public class Headers {
    public static String authToken = ConfigReader.get("authToken");
    public static String authTokenCatalogPanel = ConfigReader.get("authTokenCatalogPanel");
    public static HashMap<String,String> InspectionApiHeader()
    {
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        headers.put("X_VEHICLE_TYPE","CAR");
        headers.put("Authorization",authToken);
        headers.put("X_COUNTRY","IN");

        return headers;
    }
    public static HashMap<String,String> ApproveEstimationApiHeader()
    {
        HashMap<String,String> headers = new HashMap<>();
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("authorization", authTokenCatalogPanel);
        headers.put("content-type", "application/json");
        headers.put("x_country", "IN");
        headers.put("x_vehicle_type", "CAR");

        return headers;
    }
}
