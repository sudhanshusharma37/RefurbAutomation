package testPackage;

import commons.BaseTest;
import config.ConfigReader;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ApiResponseProcessor;

import java.lang.reflect.Method;

public class PDIFlowTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(PIFlowTest.class);
    private Response response;

    @BeforeMethod
    public void logStart(Method method) throws InterruptedException {
        log.info("Starting test: " + method.getName());
    }

    @Test(dataProvider = "Refurb_PDI_StockIn", dataProviderClass = utils.DataProviders.class)
    public void validateStockIn(String payloadType, String payload) throws InterruptedException {
        response = stockInApi.submitStockInAPI(ConfigReader.get("applicationId"),payload);
        ApiResponseProcessor.processApiResponse(payloadType,payload,"PDI inspection stockIn",response);
    }
    @Test(dataProvider = "Refurb_PDI_Inspection", dataProviderClass = utils.DataProviders.class)
    public void validatePDIInspectionSubmission(String payloadType, String payload)
    {
        response = pDIInspectionApi.submitPdiInspection(ConfigReader.get("applicationId"),payload);
        ApiResponseProcessor.processApiResponse(payloadType,payload,"PDI inspection submission",response);
    }
    @Test(dataProvider = "Refurb_Assign_Catalog", dataProviderClass = utils.DataProviders.class)
    public void validateAssignCatalog(String payloadType,String payload) {
        response = assignCatalogApi.submitAssignCatalog(ConfigReader.get("applicationId"), payload);
        ApiResponseProcessor.processApiResponse("Assign Catalog", payload, "PDI", response);
    }
    @Test(dataProvider = "Refurb_PDI_SubmitQA", dataProviderClass = utils.DataProviders.class)
    public void validateQaAssesmentSubmission(String payloadType,String payload) {
        response = qaAssesmentApi.submitQaAssesment(ConfigReader.get("applicationId"), payload);
        ApiResponseProcessor.processApiResponse("Assign Catalog", payload, "PDI", response);
    }
    @Test(dataProvider = "Refurb_PDI_Tag", dataProviderClass = utils.DataProviders.class)
    public void validateTagSubmission(String payloadType,String payload) {
        response = taggingApi.submitTag(ConfigReader.get("applicationId"), payload);
        ApiResponseProcessor.processApiResponse("Tagging and Ready_For_CheckOut Status", payload, "PDI", response);
        if(ConfigReader.get("giveTag").equals("GREEN") || ConfigReader.get("giveTag").equals("YELLOW"))
        {
            Assert.assertEquals(response.body().jsonPath().getString("data.derivedStatus"),"READY_FOR_CHECKOUT","STATUS CHECK");
        }
        else if(ConfigReader.get("giveTag").equals("RED"))
        {
            Assert.assertEquals(response.body().jsonPath().getString("data.derivedStatus"),"RED_TAG","STATUS CHECK");
        }
    }
}

