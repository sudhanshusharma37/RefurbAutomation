package utils;

import config.ConfigReader;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class DataProviders {
    @DataProvider(name = "provideInspectionPayloads")
    public static Object[][] provideInspectionPayloads() {
        List<String> catalogData = MongoUtils.getJSONRequests("inspectionType", "CATALOG", ConfigReader.get("Refurb_inspection_stage"));
        List<Object[]> data = new ArrayList<>();

        for (String json : catalogData) {
            if (json.contains("OK / No Imperfection")) {
                data.add(new Object[]{"CATALOG With No Imperfection", json});
            }
            else
            {
                data.add(new Object[]{"CATALOG With Imperfection", json});
            }
        }
        return data.toArray(new Object[0][]);
    }

    @DataProvider(name = "payloadEstimationProvider")
    public static Object[][] payloadEstimationProvider() {
        List<String> catalogData = MongoUtils.getJSONRequests("inspectionType", "CATALOG", ConfigReader.get("Refurb_Estimation_Stage"));
        List<Object[]> data = new ArrayList<>();

        for (String json : catalogData) {
                data.add(new Object[]{"CATALOG for Estimation", json});
        }
        return data.toArray(new Object[0][]);
    }
}
