package utils;

import config.ConfigReader;
import groovy.lang.DelegatesTo;
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
                data.add(new Object[]{"CATALOG Estimation", json});
        }
        return data.toArray(new Object[0][]);
    }
    @DataProvider(name = "payloadApproveEstimationProvider")
    public static Object[][] payloadApproveEstimationProvider() {
        List<String> catalogData = MongoUtils.getJSONRequests("inspectionType", "CATALOG", ConfigReader.get("Refurb_Approve_Estimation_Stage"));
        List<Object[]> data = new ArrayList<>();

        for (String json : catalogData) {
            data.add(new Object[]{"Refurb_Approve_Estimation API", json});
        }
        return data.toArray(new Object[0][]);
    }

    @DataProvider(name = "Refurb_Reject_And_Approve_Estimation")
    public static Object[][] rejectApproveEstimationFlow()
    {
        List<String> catalogData = MongoUtils.getJSONRequests("inspectionType", "CATALOG", ConfigReader.get("Refurb_Reject_And_Approve_Estimation"));
        List<Object[]> data = new ArrayList<>();

        for (String json : catalogData) {
            data.add(new Object[]{"Refurb_Reject_And_Approve_Estimation API", json});
        }
        return data.toArray(new Object[0][]);
    }
    @DataProvider(name = "Submit_Estimation_CATApproval_Stage")
    public static Object[][] submitEstimationCATPanel()
    {
        List<String> catalogData = MongoUtils.getJSONRequests("inspectionType", "CATALOG", ConfigReader.get("Refurb_Submit_Estimation_CATApproval_Stage"));
        List<Object[]> data = new ArrayList<>();

        for (String json : catalogData) {
            data.add(new Object[]{"Submit Estimation CAT API", json});
        }
        return data.toArray(new Object[0][]);
    }
    @DataProvider(name = "submitRejectedEstimation")
    public static Object[][] submitRejectedEstimation()
    {
        List<String> catalogData = MongoUtils.getJSONRequests("inspectionType", "CATALOG", ConfigReader.get("Refurb_RejectedEstimate_Submit"));
        List<Object[]> data = new ArrayList<>();

        for (String json : catalogData) {
            data.add(new Object[]{"Submit Estimation CAT API", json});
        }
        return data.toArray(new Object[0][]);
    }

    @DataProvider(name = "submitWorkProof")
    public static Object[][] submitWorkProof()
    {
        List<String> catalogData = MongoUtils.getJSONRequests("action", "estimation", ConfigReader.get("Refurb_Work_Order_Submission"));
        List<Object[]> data = new ArrayList<>();

        for (String json : catalogData) {
            data.add(new Object[]{"Submit Work Proof from Android", json});
        }
        return data.toArray(new Object[0][]);
    }
    @DataProvider(name = "Refurb_WorkOrder_Reject")
    public static Object[][] refurb_workOrder_reject()
    {
        List<String> workOrderdata = MongoUtils.getJSONRequests("action", "qc,submitQc", ConfigReader.get("Refurb_Work-Order_Reject"));
        List<Object[]> data = new ArrayList<>();

        for (String json : workOrderdata) {
            data.add(new Object[]{"Reject Work Order from CAT Panel", json});
        }
        return data.toArray(new Object[0][]);
    }

    @DataProvider(name = "Refurb_WorkOrder_Approve")
    public static Object[][] refurb_workOrder_approve()
    {
        List<String> workOrderData = MongoUtils.getJSONRequests("action","qc,submitQc", ConfigReader.get("Refurb_WorkOrder_Approve"));
        List<Object[]> data = new ArrayList<>();

        for(String json: workOrderData)
        {
            data.add(new Object[]{"Approve Work Order CAT Panel",json});
        }
        return data.toArray(new Object[0][]);
    }
    @DataProvider(name = "Refurb_Performa")
    public static Object[][] Refurb_Performa()
    {
        List<String> performaRequest = MongoUtils.getJSONRequests("action","accept",ConfigReader.get("Refurb_Performa"));
        List<Object[]> data = new ArrayList<>();
        for(String json: performaRequest)
        {
            data.add(new Object[]{"Accept Perfoma Invoice",json});
        }
        return data.toArray(new Object[0][]);
    }
}
