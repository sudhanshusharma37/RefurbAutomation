package apis.commons;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ApiClient {
    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);
    private final String baseUrl;
    private static final ThreadLocal<String> lastCurlCommand = new ThreadLocal<>();

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

        if (headers != null) {
            headers.forEach((key, value) ->
                    curl.append(" -H '").append(key).append(": ").append(value).append("'"));
        }

        if (payload != null && !payload.isEmpty()) {
            curl.append(" -d '").append(payload.replace("'", "\\'")).append("'");
        }

        return curl.toString();
    }

    private void logCurl(String method, String endpoint, Map<String, ?> headers, String payload) {
        String curl = buildCurl(method, endpoint, headers, payload);
        lastCurlCommand.set(curl);
        log.info("cURL Command: {}", curl);
    }

    public Response post(String endpoint, Map<String, String> headers, String payload) {
        logCurl("POST", endpoint, headers, payload);

        return RestAssured.given()
                .baseUri(baseUrl)
                .headers(headers)
                .body(payload)
                .post(endpoint);
    }

    public Response get(String endpoint, Map<String, String> headers) {
        logCurl("GET", endpoint, headers, null);

        return RestAssured.given()
                .baseUri(baseUrl)
                .headers(headers)
                .get(endpoint);
    }

    public Response put(String endpoint, Map<String, String> headers, String payload) {
        logCurl("PUT", endpoint, headers, payload);

        return RestAssured.given()
                .baseUri(baseUrl)
                .headers(headers)
                .body(payload)
                .put(endpoint);
    }

    public Response delete(String endpoint, Map<String, String> headers) {
        logCurl("DELETE", endpoint, headers, null);

        return RestAssured.given()
                .baseUri(baseUrl)
                .headers(headers)
                .delete(endpoint);
    }

    public static String getCurl() {
        return lastCurlCommand.get();
    }
}
