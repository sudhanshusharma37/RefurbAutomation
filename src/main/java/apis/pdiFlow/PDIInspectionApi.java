package apis.pdiFlow;

import apis.commons.ApiClient;
import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.HashMap;

public class PDIInspectionApi {
    private final ApiClient apiClient;
    private final HashMap<String,String> headers;
    public PDIInspectionApi()
    {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("inspectionApi"));
        headers = Headers.InspectionApiHeader();
    }
    public Response submitPdiInspection(String inspectionId, String payload)
    {
        return apiClient.post("/inspection/"+inspectionId,headers,payload);
    }
}
