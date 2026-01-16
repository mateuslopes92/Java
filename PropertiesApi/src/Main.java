import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        CreatePropertiesFile propertiesFile = new CreatePropertiesFile();

        propertiesFile.createInfoPropertiesFile();

        ReadingPropertiesFromFile infoProps = new ReadingPropertiesFromFile();

        System.out.println(infoProps.properties().getProperty("name"));
        System.out.println(infoProps.properties().getProperty("email"));

        // get all the system properties
        Properties systemProperties = System.getProperties();

        String javaVersion = systemProperties.getProperty("java.specification.version");

        System.out.println("JAVA VERSION USING SYSTEM = " + javaVersion);
    }
}
