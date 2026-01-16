# Java Properties API
The Properties class represents a persistent set of properties. The Properties can be saved to a stream or loaded from a stream. It belongs to java.util package. Properties define the following instance variable. This variable holds a default property list associated with a Properties object. 

## Features of Properties class:
- Properties is a subclass of Hashtable.
- It is used to maintain a list of values in which the key is a string and the value is also a string i.e; it can be used to store and retrieve string type data from the properties file.
- Properties class can specify other properties list as it's the default. If a particular key property is not present in the original Properties list, the default properties will be searched.
- Properties object does not require external synchronization and Multiple threads can share a single Properties object.
- Also, it can be used to retrieve the properties of the system.

## Basic example creating properties file (CreatePropertiesFile.java)
We can create a properties file simple by just instantiating Properties Class and setting properties to it
Then we can use FileWritter to write the file.

```
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
        p.store(new FileWriter("src/info.properties"),
                "Creating properties example");
    }
}
```

## Basic example reading properties from a file (ReadPropertiesFromFile.java)
As the creation we can simple create a file reader with FileReader Class
Create a properties object from Properties Class and load it

Then we can do whatever we want, in this case im just printing it:
```
import java.io.*;
import java.util.*;

public class ReadingPropertiesFromFile {
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
```



