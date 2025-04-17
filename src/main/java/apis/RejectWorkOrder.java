package apis;

import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.Map;

public class RejectWorkOrder {
    private final ApiClient apiClient;
    private final Map<String, String> commonHeaders;

    // Constructing our API
    public RejectWorkOrder() {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("gatewayApi"));
        commonHeaders = Headers.ApproveEstimationApiHeader();  //same headers needed for this api as ApproveEstimationApiHeader
    }

    // Calling and Capturing response from our API
    public Response rejectWorkOrder(String inspectionId, String payload) {
        return apiClient.post("/work-order/" + inspectionId+"?lang=en", commonHeaders, payload);
    }
}
