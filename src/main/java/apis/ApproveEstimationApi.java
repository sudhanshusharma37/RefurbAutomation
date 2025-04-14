package apis;

import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.Map;

public class ApproveEstimationApi {
    private final ApiClient apiClient;
    private final Map<String, Object> commonHeaders;



    // Constructing our API
    public ApproveEstimationApi() {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("estimationApprovalApiUrl"));
        commonHeaders = Headers.ApproveEstimationApiHeader();
    }

    // Calling and Capturing response from our API
    public Response submitApproveEstimationApi(String inspectionId, String payload) {
        return apiClient.post("/inspection/" + inspectionId+"?lang=en", commonHeaders, payload);

    }
}
