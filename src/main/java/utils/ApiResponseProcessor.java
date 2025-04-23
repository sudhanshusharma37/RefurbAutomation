package utils;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import reporting.ReportManager;
import com.aventstack.extentreports.ExtentTest;
import apis.commons.ApiClient;

public class ApiResponseProcessor {

    private static final Logger log = LoggerFactory.getLogger(ApiResponseProcessor.class);

    public static void processApiResponse(String payloadType, String payload, String testStage, Response response) {
        ExtentTest extentTest = ReportManager.createTest("Validate " + testStage + " - " + payloadType);

        try {
            log.info("{} API Payload: {}", testStage, payload);
            log.info("{} API Response: {}", testStage, response.asPrettyString());

            Assert.assertEquals(response.statusCode(), 200, "Expected HTTP status code 200.");

            extentTest.pass(testStage + " API call successful for payload type: " + payloadType);
            extentTest.pass("Response Status Code: " + response.statusCode());
            extentTest.info("Curl: " + ApiClient.getCurl());
            extentTest.info("Response Body: " + response.asPrettyString());

        } catch (AssertionError | Exception e) {
            log.error("{} API call failed for payload type: {}", testStage, payloadType, e);
            extentTest.fail(testStage + " API call failed for payload type: " + payloadType);
            extentTest.info("Curl: " + ApiClient.getCurl());
            extentTest.fail("Exception: " + e.getMessage());
            throw e;
        }
    }
}