# Java Reflection API
Reflection is an API that is used to examine or modify the behavior of methods, classes, and interfaces at runtime. The required classes for reflection are provided under java.lang.reflect package which is essential in order to understand reflection.

**Through reflection, we can invoke methods at runtime irrespective of the access specifier used with them.**

Reflection can be used to get information about class, constructors, and methods as depicted below in tabular format as shown:

- Class -> The getClass() method is used to get the name of the class to which an object belongs.
- Constructors -> The getConstructors() method is used to get the public constructors of the class to which an object belongs.
- Methods -> The getMethods() method is used to get the public methods of the class to which an object belongs.


## Where use reflections?
Helps to understand how java works!

In real world is used to make frameworks, for instance `Spring Framework` uses reflections all over the place, cause have to be compatible with code that hasnt being written yet.

Spring uses reflection to look at classes you created your own, and also to create objects of those classes, manipulate and inject then to other classes.

Can also use for testing, manipulating private fields to check if other methds works as supposed to be.


## Down sides
- Is easy to break things if dont know what you are doing.
- Cant do any compile time optimization(As figure out and manipulate at runtime)

## Final thoughts
If you can do without reflections is best to avoid use cause your code will be faster, more robust and more testable.

But if you have to, is a really good tool to know.