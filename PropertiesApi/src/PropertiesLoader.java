import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
        public static Properties load(String fileName) {
            Properties props = new Properties();

            try (InputStream is = PropertiesLoader.class
                    .getClassLoader()
                    .getResourceAsStream(fileName)) {

                if (is == null) {
                    throw new RuntimeException(fileName + " not found in classpath");
                }

                props.load(is);
                return props;

            } catch (IOException e) {
                throw new RuntimeException("Failed to load " + fileName, e);
            }
        }
}
