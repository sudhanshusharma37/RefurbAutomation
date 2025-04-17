package apis.piFlow;

import apis.commons.ApiClient;
import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.Map;

public class SubmitEstimateApproveReject {
    private final ApiClient apiClient;
    private final Map<String, String> commonHeaders;
    public SubmitEstimateApproveReject() {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("gatewayApi"));
        commonHeaders = Headers.ApproveEstimationApiHeader();
    }

    // Calling and Capturing response from our API
    public Response submitApproveEstimationApi(String inspectionId, String payload) {
        return apiClient.post("/inspection/" + inspectionId+"?lang=en", commonHeaders, payload);
    }
}
