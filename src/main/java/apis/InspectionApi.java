package apis;

import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.Map;

public class InspectionApi {
    private final ApiClient apiClient;
    private final Map<String, Object> commonHeaders;

    // Constructing our API
    public InspectionApi() {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("inspectionApi"));
        commonHeaders = Headers.InspectionApiHeader();
    }

    // Calling and Capturing response from our API
    public Response submitInspection(String inspectionId, String payload) {
        return apiClient.post("/inspection/" + inspectionId, commonHeaders, payload);
    }
}
