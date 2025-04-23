package apis.pdiFlow;

import apis.commons.ApiClient;
import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.HashMap;

public class TaggingApi {
    private final ApiClient apiClient;
    private final HashMap<String,String> commonHeaders;

    public TaggingApi()
    {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("inspectionApi"));
        commonHeaders = Headers.ApproveEstimationApiHeader(); // same headers as on ApproveEstimationApiHeader
    }
    public Response submitTag(String inspectioId, String payload)
    {
        return apiClient.post("/inspection/"+inspectioId+"?lang=en",commonHeaders,payload);
    }
}
