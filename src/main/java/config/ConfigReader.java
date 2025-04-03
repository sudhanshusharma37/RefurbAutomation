package config;


import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Configuration file 'config.properties' not found.");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }
    public static String get(String key)
    {
        return properties.getProperty(key);
    }

    public static String getApiBaseUrl(String apiName) {
        return properties.getProperty(apiName + "BaseUrl");
    }
}
