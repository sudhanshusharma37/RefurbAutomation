import apis.InspectionApi;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import reporting.ReportManager;
import com.aventstack.extentreports.ExtentTest;
import utils.MongoUtils;

import java.util.ArrayList;
import java.util.List;

public class InspectionApiTest {

    @DataProvider(name = "payloadProvider")
    public Object[][] providePayloads() {
        List<String> catalogData = MongoUtils.getJSONRequests("inspectionType", "CATALOG");
        List<String> noImperfectionData = MongoUtils.getJSONRequests("noImperfectionChoices", "OK / No Imperfection");

        List<Object[]> data = new ArrayList<>();
        for (String json : catalogData) {
            data.add(new Object[]{"CATALOG With Imperfection", json});
        }
        for (String json : noImperfectionData) {
            data.add(new Object[]{"CATALOG With Perfection", json});
        }

        return data.toArray(new Object[0][]);
    }

    @BeforeClass
    public void setupReport() {
        ReportManager.initializeReport();
    }

    @Test(dataProvider = "payloadProvider")
    public void validateInspectionSubmission(String payloadType, String payload) {
        ExtentTest extentTest = ReportManager.createTest("Validate Inspection Submission - " + payloadType);

        try {
            InspectionApi inspectionApi = new InspectionApi();
            Response response = inspectionApi.submitInspection("10075171601", payload);

            Assert.assertEquals(response.statusCode(), 200, "Expected HTTP status code 200.");
            response.prettyPrint();

            extentTest.pass("API call successful for payload type: " + payloadType);
            extentTest.pass("Response Status Code: " + response.statusCode());
        } catch (Exception e) {
            extentTest.fail("Test failed for payload type: " + payloadType);
            extentTest.fail("Exception: " + e.getMessage());
            throw e;
        }
    }

    @AfterClass
    public void tearDownReport() {
        ReportManager.finalizeReport();
    }
}
