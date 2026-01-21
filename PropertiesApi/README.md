# Java Properties API
The Properties class represents a persistent set of properties. The Properties can be saved to a stream or loaded from a stream. It belongs to java.util package. Properties define the following instance variable. This variable holds a default property list associated with a Properties object.

## Features of Properties class:
- Properties is a subclass of Hashtable.
- It is used to maintain a list of values in which the key is a string and the value is also a string i.e; it can be used to store and retrieve string type data from the properties file.
- Properties class can specify other properties list as it's the default. If a particular key property is not present in the original Properties list, the default properties will be searched.
- Properties object does not require external synchronization and Multiple threads can share a single Properties object.
- Also, it can be used to retrieve the properties of the system.

## Basic example creating properties file (CreatePropertiesFile.java)
We can create a properties file simple by just instantiating Properties Class and setting properties to it and using store to OutputStream out for a file
Then we can use FileWritter(for study purposes) to write the file.

```
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

```

## Basic example reading properties from a file (ReadingPropertiesFromFile.java)
As the creation we can simple create a file reader with FileReader Class(The file reader is used for study purposes, in real world we should load using resources as stream)
Create a properties object from Properties Class and load it

Then we can do whatever we want, in this case im just printing it:
```
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
```

## Basic example reading properties from a resources file using a Loader utility class and getting resource as stream (PropertiesLoader.java)

```
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
```


## On the Main.java i have the code to create a properties file, to read the created file and to read system properties as well.

*The properties files should be static files always, should not be created, as when loading is not runtime, so we need to have the files before

```

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
```


