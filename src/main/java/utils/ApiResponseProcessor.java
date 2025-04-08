package utils;

import apis.InspectionApi;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import reporting.ReportManager;
import com.aventstack.extentreports.ExtentTest;

public class ApiResponseProcessor {

    private static final Logger log = LoggerFactory.getLogger(ApiResponseProcessor.class);

    public static void processApiResponse(String payloadType, String payload, String testType, String applicationId, InspectionApi inspectionApi, Response response) {
        ExtentTest extentTest = ReportManager.createTest("Validate " + testType + " - " + payloadType);

        try {
            log.info("{} API Payload: {}", testType, payload);

            String responseBody = response.asPrettyString(); // Handle the response passed from the test
            log.info("{} API Response: {}", testType, responseBody);

            Assert.assertEquals(response.statusCode(), 200, "Expected HTTP status code 200.");
            extentTest.pass(String.format("%s API call successful for payload type: %s", testType, payloadType));
            extentTest.pass("Response Status Code: " + response.statusCode());
            extentTest.info("Response Body: " + response.prettyPrint());

        } catch (Exception e) {
            log.error("{} API call failed for payload type: {}", testType, payloadType, e);
            extentTest.fail(String.format("%s API call failed for payload type: %s", testType, payloadType));
            extentTest.fail("Exception: " + e.getMessage());
            throw e;
        }
    }
}
