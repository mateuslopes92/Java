# GOF Design Patterns

## Creational Patterns

### Singleton
This pattern is a creational pattern.
- Ensures that only one instalce of its kind exists.
- Provides a single point of access to it.
- Let you access your object from anywhere in your application
- Guarantee that only one instace of this class will be availabe at any point of time 

Eg: A database instance.

```java
class Singleton {

    // Static variable reference of single_instance
    // of type Singleton
    private static volatile Singleton single_instance = null;

    // Declaring a variable of type String
    private String s;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private Singleton(){
        s = "String from the Singleton class";
    }

    // Static method
    // Static method to create instance of Singleton class
    // The syncrhronized ensures each thread wait for its time, it locks it
    public static Singleton getInstance() {
        if(single_instance == null){
            synchronized (Singleton.class){
                if(single_instance == null){
                    single_instance = new Singleton();
                }
            }
        }

        return single_instance;
    }
}
```
- All inside the Singleton are private and has a static method getInstance to return the single instance.
- The volatile in the instance variable with the syncronized block ensures that is thread safe.

#### Conclusion
- Should be used when a class must have a single instance available
- Returns the existing instance if it has already been created
- Needs to handle multiple threads to be more robust
