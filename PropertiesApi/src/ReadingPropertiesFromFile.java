import java.io.*;
import java.util.*;

public class ReadingPropertiesFromFile {
    public Properties properties() throws Exception {
        // create a reader object on the properties file
        FileReader reader = new FileReader("src/resources/info.properties");

        // create properties object
        Properties p = new Properties();

        // Add a wrapper around reader object
        p.load(reader);

        // access properties data
        // System.out.println(p.getProperty("name"));
        // System.out.println(p.getProperty("email"));

        return p;
    }
}