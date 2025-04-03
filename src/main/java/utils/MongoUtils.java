package utils;

import com.mongodb.client.*;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MongoUtils {
    private static final Logger log = LoggerFactory.getLogger(MongoUtils.class);

    public static List<String> getJSONRequests(String key, String value) {
        MongoClient mongoClient = null;
        List<String> jsonResponses = new ArrayList<>();

        try {
            // Updated connection string
            mongoClient = MongoClients.create("mongodb+srv://b2c-be-automation-in:Ufl3DOdbWtFUj1R9@b2c-india-qa.407za.mongodb.net/admin?retryWrites=true&loadBalanced=false&replicaSet=atlas-34elyd-shard-0&readPreference=primary&srvServiceName=mongodb&connectTimeoutMS=10000&authSource=admin&authMechanism=SCRAM-SHA-1");

            // Test connection
            MongoDatabase adminDatabase = mongoClient.getDatabase("b2c-be-automation-in");
            adminDatabase.listCollectionNames().first(); // Simple test to verify the connection

            System.out.println("Connected to MongoDB successfully!");

            MongoCollection<Document> collection = adminDatabase.getCollection("Refurb");

            FindIterable<Document> documents = collection.find(new Document(key, value));

            // Iterate over documents and add to list
            for (Document doc : documents) {
                jsonResponses.add(doc.toJson());
            }

            if (jsonResponses.isEmpty()) {
                log.info("No document found with " + key + " = '" + value + "'.");
            }
        } catch (Exception e) {
            log.error("MongoDB Connection Error:", e);
        } finally {
            if (mongoClient != null) {
                mongoClient.close();  // Ensure connection is closed
            }
        }
        return jsonResponses;
    }

    public static void main(String args[]) {
        List<String> jsonRequests = getJSONRequests("inspectionType", "CATALOG");
        for (String json : jsonRequests) {
            log.info("JSON Request: " + json);
        }
    }
}
