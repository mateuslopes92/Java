import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // create a reader object on the properties file
        FileReader reader = new FileReader("src/info.properties");

        // create properties object
        Properties p = new Properties();

        // Add a wrapper around reader object
        p.load(reader);

        // access properties data
        System.out.println(p.getProperty("name"));
        System.out.println(p.getProperty("email"));
    }
}