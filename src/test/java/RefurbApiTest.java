import apis.InspectionApi;
import com.aventstack.extentreports.ExtentTest;
import config.ConfigReader;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import reporting.ReportManager;
import utils.ApiResponseProcessor;

public class RefurbApiTest {

    private static Response response = null;
    private static final Logger log = LoggerFactory.getLogger(RefurbApiTest.class);

    private static final String applicationId = ConfigReader.get("applicationId");
    private InspectionApi inspectionApi; // Declare InspectionApi as an instance variable

    @BeforeClass
    public void setup() {
        ReportManager.initializeReport(); // Initialize Extent Report
        inspectionApi = new InspectionApi(); // Initialize the InspectionApi object
    }

    @Test(priority = 0, dataProvider = "provideInspectionPayloads", dataProviderClass = utils.DataProviders.class)
    public void validateInspectionSubmission(String payloadType, String payload) {
        response = inspectionApi.submitInspection(applicationId, payload); // Moved here
        ApiResponseProcessor.processApiResponse(payloadType, payload, "Inspection", applicationId, inspectionApi, response);
    }

    @Test(priority = 1)
    public void checkInspected() {
        ExtentTest extentTest = ReportManager.createTest("Check Inspection Status");
        try {
            String derivedStatus = response.body().jsonPath().getString("data.derivedStatus");
            Assert.assertEquals(derivedStatus, "INSPECTED", "Derived status should be 'INSPECTED'.");
            extentTest.pass("Inspection status is 'INSPECTED'.");
        } catch (Exception e) {
            log.error("Error during inspection status check: ", e);
            extentTest.fail("Error during inspection status check: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2, dataProvider = "payloadEstimationProvider", dataProviderClass = utils.DataProviders.class)
    public void validateEstimationSubmission(String payloadType, String payload) {
        response = inspectionApi.submitInspection(applicationId, payload); // Moved here
        ApiResponseProcessor.processApiResponse(payloadType, payload, "Estimation", applicationId, inspectionApi, response);
    }

    @AfterClass
    public void tearDownReport() {
        ReportManager.finalizeReport();
    }
}
