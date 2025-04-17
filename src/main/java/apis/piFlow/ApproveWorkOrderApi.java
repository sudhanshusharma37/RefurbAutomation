package apis.piFlow;

import apis.commons.ApiClient;
import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.Map;

public class ApproveWorkOrderApi {
    private final ApiClient apiClient;
    private final Map<String, String> headers;

    public ApproveWorkOrderApi()
    {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("gatewayApi"));
        headers = Headers.ApproveEstimationApiHeader();  //same headers needed for this api as ApproveEstimationApiHeader
    }
    public Response submitWorkOrderApproval(String inspectionId, String payload)
    {
        return apiClient.post("/work-order/" + inspectionId+"?lang=en", headers, payload);
    }
}
