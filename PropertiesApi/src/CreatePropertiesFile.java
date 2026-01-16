import java.io.*;
import java.util.*;

public class CreatePropertiesFile {
    public static void main(String[] args) throws Exception {
        // create an instance of Properties
        Properties p = new Properties();

        // add properties to it
        p.setProperty("name", "Mateus Lopes");
        p.setProperty("email",
                "mateuslopes92@gmail.com");

        // store the properties to a file
        p.store(new FileWriter("src/resources/info.properties"),
                "Creating properties example");
    }
}
