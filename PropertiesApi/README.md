# Java Properties API
The Properties class represents a persistent set of properties. The Properties can be saved to a stream or loaded from a stream. It belongs to java.util package. Properties define the following instance variable. This variable holds a default property list associated with a Properties object. 

## Features of Properties class:
- Properties is a subclass of Hashtable.
- It is used to maintain a list of values in which the key is a string and the value is also a string i.e; it can be used to store and retrieve string type data from the properties file.
- Properties class can specify other properties list as it's the default. If a particular key property is not present in the original Properties list, the default properties will be searched.
- Properties object does not require external synchronization and Multiple threads can share a single Properties object.
- Also, it can be used to retrieve the properties of the system.

## Basic example
Just creating an db.properties file and storing username and password:
```
username = student
password = neverstop
```

Retrieving from the main class:
```
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // create a reader object on the properties file
        FileReader reader = new FileReader("src/db.properties");

        // create properties object
        Properties p = new Properties();

        // Add a wrapper around reader object
        p.load(reader);

        // access properties data
        System.out.println(p.getProperty("username"));
        System.out.println(p.getProperty("password"));
    }
}
```

