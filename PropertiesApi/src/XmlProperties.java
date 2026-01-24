import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class XmlProperties {
    public static void read() throws IOException {
        Properties p = new Properties();

        try(FileInputStream fis = new FileInputStream("src/resources/properties.xml")){
            /**
             * Loads all of the properties represented by
             * the XML document on the specified input
             * stream into this properties table.
             */
            p.loadFromXML(fis);

            Enumeration<?> enumeration = p.propertyNames();

            while(enumeration.hasMoreElements()){
                String key = (String) enumeration.nextElement();
                String value = p.getProperty(key);

                System.out.println(key + " = " + value);
            }
        }
    }
}
