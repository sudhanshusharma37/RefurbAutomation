package apis.pdiFlow;

import apis.commons.ApiClient;
import config.ConfigReader;
import io.restassured.response.Response;
import utils.Headers;

import java.util.HashMap;

public class StockInApi {
    private final ApiClient apiClient;
    private final HashMap<String,String> commonHeaders;

    public StockInApi()
    {
        apiClient = new ApiClient(ConfigReader.getApiBaseUrl("inspectionApi"));
        commonHeaders = Headers.InspectionApiHeader();
    }
    public Response submitStockInAPI(String inspectioId, String payload)
    {
        return apiClient.post("/inspection/"+inspectioId+"?ri=true&piPdi=true",commonHeaders,payload);
    }
}
