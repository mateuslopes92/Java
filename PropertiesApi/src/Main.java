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
        System.out.println("----Reading from a created file properties ----");
        ReadingPropertiesFromFile propReader = new ReadingPropertiesFromFile();
        Properties infoProps = propReader.properties();
        System.out.println(infoProps.getProperty("name"));
        System.out.println(infoProps.getProperty("email"));
        System.out.println("-----------------------------\n");

        // Reading resources using getResourcesAsStream
        Properties dbInfo = PropertiesLoader.load("db.properties");
        System.out.println("----Database properties ----");
        System.out.println(dbInfo.getProperty("db.url"));
        System.out.println(dbInfo.getProperty("db.user"));
        System.out.println("-----------------------------\n");


        // get all the system properties
        System.out.println("----System properties ----");
        Properties systemProperties = System.getProperties();
        String javaVersion = systemProperties.getProperty("java.specification.version");
        System.out.println("JAVA VERSION USING SYSTEM = " + javaVersion);
        System.out.println("-----------------------------\n");

        // Getting all properties from XML properties file
        System.out.println("----XML properties ----");
        XmlProperties xmlProperties = new XmlProperties();
        xmlProperties.read();
        System.out.println("-----------------------------\n");
    }
}
