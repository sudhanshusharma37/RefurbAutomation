package apis.piFlow;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class demo {
   public static void main(String[] args) {
      // Base URI
      RestAssured.baseURI = "https://stage-refurbishment-service.qac24svc.dev";

      // Send GET request
      Response response = RestAssured
              .given()
              .header("Accept", "application/json, text/plain, */*")
              .header("X_VEHICLE_TYPE", "CAR")
              .header("X_APP_VERSION", "4.0.5")
              .header("X_BUILD_NUMBER", "33")
              .header("Authorization", "Bearer eyJraWQiOiJNaHh3QVJ4dFVyT01tNUZNQnlPNTM5Q2ZxWVVSUmhLY0RXbzhKU013VlhNIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULmZOelBpYkE4a1J6MUhLd1d1MmlZV0dsa3ktd3Mtb1NidFNKbDlmTnZPR2cub2FyMWFxOTB6MlRwUVptbmUxZDciLCJpc3MiOiJodHRwczovL2NhcnMyNC5va3RhcHJldmlldy5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNzQ0NzExNDQyLCJleHAiOjE3NDQ3OTc4NDIsImNpZCI6IjBvYXJheXViZVdCbmt3UzFVMWQ2IiwidWlkIjoiMDB1M2ppazA1YmhzUU9TdUwxZDciLCJzY3AiOlsib2ZmbGluZV9hY2Nlc3MiLCJvcGVuaWQiLCJwcm9maWxlIl0sImF1dGhfdGltZSI6MTc0NDcxMTQzOSwic3ViLW5hbWUiOiJ3c20uc2NAY2FyczI0LmNvbSIsImxhc3ROYW1lIjoiU0MiLCJzdWIiOiJ3c20uc2NAY2FyczI0LmNvbSIsImNvdW50cnkiOlsiSU4iXSwiZmlyc3ROYW1lIjoiV1NNIiwiVEVTVF9DUkVBVEVfTU9WRU1FTlQiOmZhbHNlLCJncm91cHMiOlsiRXZlcnlvbmUiLCJDQVRBTE9HX1RFQ0giLCJDQVRBTE9HX1dTTSIsIlZFTkRPUl9JTlZPSUNFIl0sImxvY2F0aW9ucyI6WyJMQ18yNTQiLCJMQ18yMjEiLCJMQ18xODEiXSwiem9uZXMiOltdLCJ2ZWhpY2xlVHlwZSI6WyJDQVIiXX0.NUXY3wnHG-60QMKEndHwSyq1Pf8BH7juCKqmByhzpVv1kBL_M4XaMvaqgoDhxfpns2PQZ7tuGu9HLhWBp2ViADHN-YC_8pu6z_p7vZLsjvVrNEbCTu6QVd2aJuvybdOBTvzNaCviI9Akhej1-qTssv6WWZpoK1Zm0jQUgbNilVZNzvzC1PdWnlEXll5GcsSRFSwwiHWzcV5jnzLjrcJ5PlAU8o3O_8TbRc8uh1wCPQc6AhQrbAummz5oglf_02a7hils7bFLhK44WUJm7SwqatVCQQqqfG5t6pe2TcCA-OLcSvJddH33Z1-QxiT8LLkJDyMzRPFTMKQo0DrKUMwd1A")
              .header("X_COUNTRY", "IN")
              .when()
              .get("/work-order?appointmentId=10075172130")
              .then()
              .statusCode(200)
              .extract().response();

      Map<String, Object> qualityChecks = response.jsonPath().getMap("[0].qualityChecks");

      // Convert keys to a List
      List<String> qualityCheckNames = new ArrayList<>(qualityChecks.keySet());
   }
}
