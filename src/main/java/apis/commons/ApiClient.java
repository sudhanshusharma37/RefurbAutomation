package apis.commons;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ApiClient {
    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);
    private final String baseUrl;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private String buildCurl(String method, String endpoint, Map<String, ?> headers, String payload) {
        StringBuilder curl = new StringBuilder("curl -X ")
                .append(method.toUpperCase())
                .append(" '")
                .append(baseUrl)
                .append(endpoint)
                .append("'");

        headers.forEach((key, value) -> curl.append(" -H '").append(key).append(": ").append(value).append("'"));

        if (payload != null && !payload.isEmpty()) {
            curl.append(" -d '").append(payload.replace("'", "\\'")).append("'");
        }

        return curl.toString();
    }

    public Response post(String endpoint, Map<String, String> headers, String payload) {
        String curl = buildCurl("POST", endpoint, headers, payload);
        log.info("POST cURL Command: {}", curl);

        return RestAssured.given()
                .baseUri(baseUrl)
                .headers(headers)
                .body(payload)
                .post(endpoint);
    }

    public Response get(String endpoint, Map<String, String> headers) {
        String curl = buildCurl("GET", endpoint, headers, null);
        log.info("GET cURL Command: {}", curl);

        return RestAssured.given()
                .baseUri(baseUrl)
                .headers(headers)
                .get(endpoint);
    }

    public Response put(String endpoint, Map<String, String> headers, String payload) {
        String curl = buildCurl("PUT", endpoint, headers, payload);
        log.info("PUT cURL Command: {}", curl);

        return RestAssured.given()
                .baseUri(baseUrl)
                .headers(headers)
                .body(payload)
                .put(endpoint);
    }
}
