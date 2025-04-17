package apis;

import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.Map;

public class SubmitWorkProof {
    private final ApiClient apiClient;
    private final Map<String, String> commonHeaders;

    // Constructing our API
    public SubmitWorkProof() {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("inspectionApi"));
        commonHeaders = Headers.InspectionApiHeader();
    }

    public Response submitProofOfWork(String inspectionId, String payload) {
        return apiClient.post("/work-order/" + inspectionId, commonHeaders, payload);
    }
}
