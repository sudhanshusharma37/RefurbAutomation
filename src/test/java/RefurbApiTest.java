
import io.restassured.response.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import reporting.ReportManager;
import utils.ApiResponseProcessor;

import java.util.HashMap;

public class RefurbApiTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(RefurbApiTest.class);
    private Response response;

    @Test(dataProvider = "provideInspectionPayloads", dataProviderClass = utils.DataProviders.class)
    public void validateInspectionSubmission(String payloadType, String payload) {
        response = inspectionApi.submitInspection(applicationId, payload);
        ApiResponseProcessor.processApiResponse(payloadType, payload, "Inspection", response);
    }

    @Test
    public void validateInspectionStatusIsInspected() {
        var extentTest = ReportManager.createTest("Validate Derived Status - INSPECTED");
        try {
            String status = response.jsonPath().getString("data.derivedStatus");
            Assert.assertEquals(status, "INSPECTED", "Expected derivedStatus to be 'INSPECTED'");
            extentTest.pass("Derived status is 'INSPECTED'");
        } catch (Exception e) {
            log.error("Error validating derived status: ", e);
            extentTest.fail("Error validating derived status: " + e.getMessage());
            throw e;
        }
    }

    @Test(dataProvider = "payloadEstimationProvider", dataProviderClass = utils.DataProviders.class)
    public void validateEstimationSubmission(String payloadType, String payload) {
        response = inspectionApi.submitInspection(applicationId, payload);
        ApiResponseProcessor.processApiResponse(payloadType, payload, "Estimation", response);
    }

    @Test
    public void validateAssignCatalogApi() {
        HashMap<String, String> payloadMap = new HashMap<>();
        payloadMap.put("appointmentId", applicationId);
        payloadMap.put("inspectionType", "CATALOG");

        String payload = new JSONObject(payloadMap).toString();
        response = assignCatalogApi.submitAssignCatalog(applicationId, payload);

        ApiResponseProcessor.processApiResponse("Assign Catalog", payload, "Estimation", response);
    }

    @Test(dataProvider = "Refurb_Reject_And_Approve_Estimation", dataProviderClass = utils.DataProviders.class)
    public void validateEstimationRejectionFlow(String payloadType, String payload) {
        response = estimationRejectFlowApi.submitEstimationRejectFlowApi(applicationId, payload);
        ApiResponseProcessor.processApiResponse(payloadType, payload, "Estimation Reject", response);
    }

    @Test(dataProvider = "Submit_Estimation_CATApproval_Stage", dataProviderClass = utils.DataProviders.class)
    public void submitEstimationCat(String payloadType, String payload) {
        response = submitEstimateApproveReject.submitApproveEstimationApi(applicationId, payload);
        ApiResponseProcessor.processApiResponse(payloadType, payload, "Estimation Approval", response);
    }

    @Test(dataProvider = "submitRejectedEstimation", dataProviderClass = utils.DataProviders.class)
    public void submitRejectEstimation(String payloadType, String payload) {
        response = estimationRejectFlowApi.submitRejectEstimation(applicationId, payload);
        ApiResponseProcessor.processApiResponse(payloadType, payload, "Estimation Approval", response);
    }

    @Test(dataProvider = "payloadApproveEstimationProvider", dataProviderClass = utils.DataProviders.class)
    public void validateEstimationApprovalFlow(String payloadType, String payload) {
        response = approveEstimationApi.submitApproveEstimationApi(applicationId, payload);
        ApiResponseProcessor.processApiResponse(payloadType, payload, "Estimation Approval", response);
    }
}