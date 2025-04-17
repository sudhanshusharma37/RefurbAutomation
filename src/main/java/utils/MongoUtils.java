package utils;

import com.mongodb.client.*;
import config.ConfigReader;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoUtils {
    private static final Logger log = LoggerFactory.getLogger(MongoUtils.class);

    private static final String CONNECTION_STRING = "mongodb+srv://b2c-be-automation-in:Ufl3DOdbWtFUj1R9@b2c-india-qa.407za.mongodb.net/admin?retryWrites=true&loadBalanced=false&replicaSet=atlas-34elyd-shard-0&readPreference=primary&srvServiceName=mongodb&connectTimeoutMS=10000&authSource=admin&authMechanism=SCRAM-SHA-1";

    /**
     * Fetch documents where {key = value}
     */
    public static List<String> getJSONRequests(String key, String value, String collectionName) {
        MongoClient mongoClient = null;
        List<String> jsonResponses = new ArrayList<>();

        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            MongoDatabase database = mongoClient.getDatabase("b2c-be-automation-in");
            MongoCollection<Document> collection = database.getCollection(collectionName);

            FindIterable<Document> documents;
            if (value.contains(",")) {
                List<String> values = Arrays.asList(value.split(","));
                documents = collection.find(new Document(key, new Document("$in", values)));
            } else {
                documents = collection.find(new Document(key, value));
            }

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
                mongoClient.close();
            }
        }

        return jsonResponses;
    }

    public static List<String> getJSONByCheckpointKey(String collectionName,String checkpointKey) {
        MongoClient mongoClient = null;
        List<String> jsonResponses = new ArrayList<>();

        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            MongoDatabase database = mongoClient.getDatabase("b2c-be-automation-in");
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Build nested query: data.checkpoints.{checkpointKey}: { $exists: true }
            String nestedKey = "data.checkpoints." + checkpointKey;
            Document query = new Document(nestedKey, new Document("$exists", true));

            FindIterable<Document> documents = collection.find(query);

            for (Document doc : documents) {
                jsonResponses.add(doc.toJson());
            }

            if (jsonResponses.isEmpty()) {
                log.info("No document found where " + nestedKey + " exists.");
            }
        } catch (Exception e) {
            log.error("MongoDB Error in getJSONByCheckpointKey:", e);
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }

        return jsonResponses;
    }
}
