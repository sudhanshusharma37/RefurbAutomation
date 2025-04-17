package apis;

import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.Map;

public class AssignWorkOrderApi {

    private final ApiClient apiClient;
    private final Map<String, String> commonHeaders;

    // Constructing our API
    public AssignWorkOrderApi() {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("gatewayApi"));
        commonHeaders = Headers.ApproveEstimationApiHeader();  //same headers needed for this api as ApproveEstimationApiHeader
    }

    // Calling and Capturing response from our API
    public Response submitWorkOrderAssign(String inspectionId, String payload) {
        return apiClient.put("/work-order/assign/" + inspectionId+"?lang=en", commonHeaders, payload);
    }
}
