# Java Reflection API
Reflection is an API that is used to examine or modify the behavior of methods, classes, and interfaces at runtime. The required classes for reflection are provided under java.lang.reflect package which is essential in order to understand reflection. 

**Through reflection, we can invoke methods at runtime irrespective of the access specifier used with them.**

Reflection can be used to get information about class, constructors, and methods as depicted below in tabular format as shown:

- Class -> The getClass() method is used to get the name of the class to which an object belongs.
- Constructors -> The getConstructors() method is used to get the public constructors of the class to which an object belongs.
- Methods -> The getMethods() method is used to get the public methods of the class to which an object belongs.