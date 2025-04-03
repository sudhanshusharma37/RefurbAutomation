package utils;


import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class SchemaValidator {

    public static boolean validate(String payload, String schemaPath) {
        try {
            JsonSchema schema = JsonSchemaFactory.byDefault().getJsonSchema(new File(schemaPath).toURI().toString());
            return schema.validate(new ObjectMapper().readTree(payload)).isSuccess();
        } catch (Exception e) {
            throw new RuntimeException("Schema validation failed", e);
        }
    }
}
