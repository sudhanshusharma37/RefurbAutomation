package apis;

import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class InspectionApi {
    private final ApiClient apiClient;
    private final Map<String, String> commonHeaders;
    private static final Logger log = LoggerFactory.getLogger(InspectionApi.class);

    // Constructing our API
    public InspectionApi() {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("inspectionApi"));
        commonHeaders = Headers.InspectionApiHeader();
    }

    // Calling and Capturing response from our API
    public Response submitInspection(String inspectionId, String payload) {
        System.out.println();
        log.info("Base URI is: "+ConfigReader.getApiBaseUrl("inspectionApi"));
        return apiClient.post("/inspection/" + inspectionId, commonHeaders, payload);
    }
}
