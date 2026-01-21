import java.io.*;
import java.util.*;

public class CreatePropertiesFile {
    public void createInfoPropertiesFile() throws Exception {
        // Holds key-value configuration loaded from a properties file
        Properties p = new Properties();

        // Define configuration values (keys and values must be strings)
        p.setProperty("name", "Mateus Lopes");
        p.setProperty("email",
                "mateuslopes92@gmail.com");

        // store the properties to a file
        // creating this file for testing purposes
        // resources is not a runtime folder, should have static files only
        p.store(new FileWriter("src/resources/info.properties"),
                "Creating properties example");
    }
}
