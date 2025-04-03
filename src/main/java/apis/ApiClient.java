package apis;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;


public class ApiClient {
    public ApiClient(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }

    public Response post(String endpoint, Map<String, Object> headers, String payload) {
        return RestAssured.given()
                .headers(headers)
                .body(payload)
                .post(endpoint);
    }

    public Response get(String endpoint, Map<String, String> headers) {
        return RestAssured.given()
                .headers(headers)
                .get(endpoint);
    }
}
