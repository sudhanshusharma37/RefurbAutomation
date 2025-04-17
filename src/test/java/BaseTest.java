import apis.*;
import config.ConfigReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import reporting.ReportManager;

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

    @BeforeClass
    public void setup() {
        ReportManager.initializeReport();
        applicationId = ConfigReader.get("applicationId");

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
    }

    @AfterClass
    public void tearDown() {
        ReportManager.finalizeReport();
    }
}