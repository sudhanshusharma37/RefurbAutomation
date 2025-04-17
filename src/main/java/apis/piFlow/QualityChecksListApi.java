package apis.piFlow;

import apis.commons.ApiClient;
import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.HashMap;

public class QualityChecksListApi {
    private final ApiClient apiClient;
    private final HashMap<String, String> commonHeaders;
    public QualityChecksListApi() {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("gatewayApi"));
        commonHeaders = Headers.ApproveEstimationApiHeader();
    }

    // Calling and Capturing response from our API
    public Response submitQCList(String inspectionId) {
        return apiClient.get("/work-order?appointmentId=" + inspectionId, commonHeaders);
    }
}
