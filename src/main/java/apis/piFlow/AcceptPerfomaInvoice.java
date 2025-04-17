package apis.piFlow;

import apis.commons.ApiClient;
import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.Map;

public class AcceptPerfomaInvoice {
    private final ApiClient apiClient;
    private final Map<String, String> commonHeaders;

    public AcceptPerfomaInvoice()
    {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("inspectionApi"));
        commonHeaders = Headers.ApproveEstimationApiHeader();
    }
    public Response submitPerfomaInvoice(String inspectionId, String payload)
    {
        return apiClient.post("/invoice/proforma/"+inspectionId+"?lang=en",commonHeaders,payload);
    }
}
