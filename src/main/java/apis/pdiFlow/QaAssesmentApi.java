package apis.pdiFlow;

import apis.commons.ApiClient;
import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.HashMap;

public class QaAssesmentApi {
    private final ApiClient apiClient;
    private final HashMap<String,String> commonHeaders;

    public QaAssesmentApi()
    {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("inspectionApi"));
        commonHeaders = Headers.ApproveEstimationApiHeader(); // same headers as on ApproveEstimationApiHeader
    }
    public Response submitQaAssesment(String inspectioId, String payload)
    {
        return apiClient.post("/inspection/"+inspectioId+"?lang=en",commonHeaders,payload);
    }
}
