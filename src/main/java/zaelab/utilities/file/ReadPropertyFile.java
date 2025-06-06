package zaelab.utilities.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertyFile {

    /**
     * This method return the value of given key present in property file
     */
    public static String getProperty(String Property, String filePath) {
        try {
            Properties prop = loadProperties(filePath);
            return prop.getProperty(Property);
        } catch (Exception ex) {
            System.err.println("Error reading property: " + Property + " from file: " + filePath);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * This method loads the property file
     */
    public static Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            // First try to load from classpath
            inputStream = ReadPropertyFile.class.getClassLoader().getResourceAsStream(filePath);
            
            // If not found in classpath, try system classloader
            if (inputStream == null) {
                inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
            }
            
            // If still not found, try as a file
            if (inputStream == null) {
                File file = new File(filePath);
                if (file.exists()) {
                    inputStream = new FileInputStream(file);
                } else {
                    throw new IOException("Could not find property file: " + filePath);
                }
            }
            
            properties.load(inputStream);
            return properties;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * This method returns the InputStream of given file
     */
    public static InputStream loadResource(String filePath) throws IOException {
        ClassLoader classLoader = ReadPropertyFile.class.getClassLoader();
        InputStream inputStream = null;
        if (classLoader != null) {
            inputStream = classLoader.getResourceAsStream(filePath);
        }
        if (inputStream == null) {
            classLoader = ClassLoader.getSystemClassLoader();
            if (classLoader != null) {
                inputStream = classLoader.getResourceAsStream(filePath);
            }
        }
        if (inputStream == null) {
            File file = new File(filePath);
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            }
        }
        return inputStream;
    }
}
