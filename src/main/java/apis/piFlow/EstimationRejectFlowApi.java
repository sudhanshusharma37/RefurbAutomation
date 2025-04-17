package apis.piFlow;

import apis.commons.ApiClient;
import config.ConfigReader;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Headers;

import java.util.Map;

public class EstimationRejectFlowApi {

    private static final Logger log = LoggerFactory.getLogger(EstimationRejectFlowApi.class);
    private final ApiClient apiClient;
    private final Map<String, String> commonHeaders;
    private final InspectionApi inspectionApi = new InspectionApi();
    private static String payload = "";
    private static String inspectionId = "";

    // Constructor to initialize API client and headers
    public EstimationRejectFlowApi() {
        this.apiClient = new ApiClient(ConfigReader.getApiBaseUrl("gatewayApi"));
        this.commonHeaders = Headers.ApproveEstimationApiHeader();
    }

    // POST request to estimation reject flow API
    public Response submitEstimationRejectFlowApi(String inspectionId, String payload) {
        EstimationRejectFlowApi.inspectionId = inspectionId;
        return apiClient.post("/inspection/" + inspectionId + "?lang=en", commonHeaders, payload);
    }

    // This method fetches a document from MongoDB based on checkpoint key and uses it to re-submit inspection
    public Response submitRejectEstimation(String inspectionId, String payload) {
        EstimationRejectFlowApi.inspectionId = inspectionId;
        return apiClient.post("/inspection/" + inspectionId + "?lang=en", Headers.InspectionApiHeader(), payload);
    }
}