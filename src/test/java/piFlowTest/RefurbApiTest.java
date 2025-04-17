package piFlowTest;

import io.restassured.response.Response;
import org.bson.Document;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import reporting.ReportManager;
import utils.ApiResponseProcessor;
import commons.BaseTest;

import java.lang.reflect.Method;
import java.util.*;

public class RefurbApiTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(RefurbApiTest.class);
    private Response response;

    @BeforeMethod
    public void logStart(Method method) {
        log.info("Starting test: " + method.getName());
    }

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

    @Test(dataProvider = "submitWorkProof", dataProviderClass = utils.DataProviders.class)
    public void submitWorkProofFromAndroid(String payloadType, String payload) throws InterruptedException {
        Thread.sleep(2000);

        Document originalRequest = Document.parse(payload);
        String action = originalRequest.getString("action");
        Map<String, Object> checkpoints = (Map<String, Object>) originalRequest.get("checkpoints");
        Map<String, Object> estimates = (Map<String, Object>) originalRequest.get("estimates");

        Map<String, Object> filteredCheckpoints = new HashMap<>();
        Map<String, Object> filteredEstimates = new HashMap<>();
        Response qcList = qualityChecksListApi.submitQCList(applicationId);
        Map<String, Object> qualityChecks = qcList.jsonPath().getMap("[0].qualityChecks");

        List<String> requiredKeys = new ArrayList<>(qualityChecks.keySet());

        for (String key : requiredKeys) {
            if (checkpoints.containsKey(key)) {
                filteredCheckpoints.put(key, checkpoints.get(key));
            }
            if (estimates.containsKey(key)) {
                filteredEstimates.put(key, estimates.get(key));
            }
        }

        Map<String, Object> finalRequestMap = new HashMap<>();
        finalRequestMap.put("action", action);
        finalRequestMap.put("checkpoints", filteredCheckpoints);
        finalRequestMap.put("estimates", filteredEstimates);

        String finalPayload = new Document(finalRequestMap).toJson();
        response = submitWorkProof.submitProofOfWork(applicationId, finalPayload);

        ApiResponseProcessor.processApiResponse(payloadType, payload, "Work Proof Submission", response);
    }

    @Test(dataProvider = "submitWorkProof", dataProviderClass = utils.DataProviders.class)
    public void resubmitWorkProofFromAndroid(String payloadType, String payload) throws InterruptedException {
        submitWorkProofFromAndroid(payloadType, payload); // sampleWorkProofPayload must be a valid string payload
    }


    @Test
    public void validateAssignWorkOrder() {
        HashMap<String, String> payloadMap = new HashMap<>();
        payloadMap.put("appointmentId", applicationId);

        String payload = new JSONObject(payloadMap).toString();
        response = assignWorkOrderApi.submitWorkOrderAssign(applicationId, payload);

        ApiResponseProcessor.processApiResponse("Assign", payload, "Work-Order", response);
    }

    @Test(dataProvider = "Refurb_WorkOrder_Reject", dataProviderClass = utils.DataProviders.class)
    public void validateRejectWorkOrderCAT(String payLoadtype, String payLoad) {
        response = rejectWorkOrder.rejectWorkOrder(applicationId, payLoad);
        ApiResponseProcessor.processApiResponse("Reject", payLoad, "Work-Order", response);
    }

    @Test(dataProvider = "Refurb_WorkOrder_Approve", dataProviderClass = utils.DataProviders.class)
    public void validateApproveWorkOrderCAT(String payLoadtype, String payLoad) {
        response = approveWorkOrderApi.submitWorkOrderApproval(applicationId, payLoad);
        ApiResponseProcessor.processApiResponse("Approve", payLoad, "Work-Order", response);
    }

    @Test(dataProvider = "Refurb_Performa", dataProviderClass = utils.DataProviders.class)
    public void acceptPerfoma(String payloadType,String payLoad)
    {
        response = acceptPerfomaInvoice.submitPerfomaInvoice(applicationId,payLoad);
        ApiResponseProcessor.processApiResponse("Perfoma-Invoice", payLoad, "Perfoma-Invoice Acceptance", response);
    }
}
