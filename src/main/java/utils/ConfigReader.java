package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    // Static block to load properties when the class is accessed
    static {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config/config.properties")) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + e.getMessage());
        }
    }

    // Method to fetch property values
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    //Overloaded with default value
    public static String getProperty(String key, String defValue){
        String value = properties.getProperty(key);
        return  (value == null || value.trim().isEmpty()) ? defValue : value;
    }


    public static int getIntProperty(String key) {
        String value = properties.getProperty(key);

        if (value == null){
            throw new RuntimeException("Missing integer property: " + key);
        }
        try{
            return Integer.parseInt(value.trim());
        }catch (NumberFormatException e){
            throw new RuntimeException("Invalid integer value for property: " + key + " = " + value, e);
        }
    }
}