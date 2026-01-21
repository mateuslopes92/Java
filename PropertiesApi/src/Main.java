import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        // *CREATE/STORE PROPERTIES*
        // Uncomment bellow if want to create a info.properties file for testing
        //        CreatePropertiesFile propertiesFile = new CreatePropertiesFile();
        //        propertiesFile.createInfoPropertiesFile();

        // *READ PROPERTIES*
        // Uncomment bellow to see the properties loaded from the info.properties file created above
        //        ReadingPropertiesFromFile propReader = new ReadingPropertiesFromFile();
        //        Properties infoProps = propReader.properties();
        //        System.out.println(infoProps.getProperty("name"));
        //        System.out.println(infoProps.getProperty("email"));

        // Reading resources using getResourcesAsStream
        Properties dbInfo = PropertiesLoader.load("db.properties");
        System.out.println("----Database properties ----");
        System.out.println(dbInfo.getProperty("db.url"));
        System.out.println(dbInfo.getProperty("db.user"));
        System.out.println("-----------------------------\n");


        // get all the system properties
        Properties systemProperties = System.getProperties();
        String javaVersion = systemProperties.getProperty("java.specification.version");
        System.out.println("JAVA VERSION USING SYSTEM = " + javaVersion);
    }
}
