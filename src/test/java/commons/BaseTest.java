package commons;

import apis.pdiFlow.*;
import apis.piFlow.*;
import config.ConfigReader;
import org.testng.annotations.*;
import reporting.ReportManager;
import utils.EmailUtils;

public class BaseTest {

    protected String applicationId;
    protected InspectionApi inspectionApi;
    protected ApproveEstimationApi approveEstimationApi;
    protected AssignCatalogApi assignCatalogApi;
    protected EstimationRejectFlowApi estimationRejectFlowApi;
    protected SubmitEstimateApproveReject submitEstimateApproveReject;
    protected SubmitWorkProof submitWorkProof;
    protected QualityChecksListApi qualityChecksListApi;
    protected AssignWorkOrderApi assignWorkOrderApi;
    protected RejectWorkOrder rejectWorkOrder;
    protected ApproveWorkOrderApi approveWorkOrderApi;
    protected AcceptPerfomaInvoice acceptPerfomaInvoice;
    //PDI-FLOW
    protected QaAssesmentApi qaAssesmentApi;
    protected StockInApi stockInApi;
    protected PDIInspectionApi pDIInspectionApi;
    protected AssignCatalog pdiAssignCatalog;
    protected TaggingApi taggingApi;

    @BeforeTest
    public void setupReport()
    {
        applicationId = ConfigReader.get("applicationId");
        ReportManager.initializeReport();
    }
    @BeforeClass
    public void setup() {

        inspectionApi = new InspectionApi();
        approveEstimationApi = new ApproveEstimationApi();
        assignCatalogApi = new AssignCatalogApi();
        estimationRejectFlowApi = new EstimationRejectFlowApi();
        submitEstimateApproveReject = new SubmitEstimateApproveReject();
        submitWorkProof = new SubmitWorkProof();
        qualityChecksListApi = new QualityChecksListApi();
        assignWorkOrderApi = new AssignWorkOrderApi();
        rejectWorkOrder = new RejectWorkOrder();
        approveWorkOrderApi = new ApproveWorkOrderApi();
        acceptPerfomaInvoice = new AcceptPerfomaInvoice();
        //PDI - Flow
        stockInApi = new StockInApi();
        pDIInspectionApi = new PDIInspectionApi();
        pdiAssignCatalog = new AssignCatalog();
        qaAssesmentApi = new QaAssesmentApi();
        taggingApi = new TaggingApi();
    }
    @AfterClass

    @AfterTest
    public void tearDown() {
        ReportManager.finalizeReport();
        EmailUtils.sendReportByEmail("test-output/ExtentReport.html");
    }
}