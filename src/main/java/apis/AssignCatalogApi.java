package apis;

import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.Map;

public class AssignCatalogApi {
    private final ApiClient apiClient;
    private final Map<String, Object> commonHeaders;

    // Constructing our API
    public AssignCatalogApi() {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("gaetwayApi"));
        commonHeaders = Headers.ApproveEstimationApiHeader();
    }

    // Calling and Capturing response from our API
    public Response submitAssignCatalog(String inspectionId, String payload) {
        return apiClient.put("/catalog/assign/" + inspectionId+"?inspectionType=CATALOG&lang=en", commonHeaders, payload);
    }
}
