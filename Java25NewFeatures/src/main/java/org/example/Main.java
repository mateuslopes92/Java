package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        VirtualThreadExample.main(args);
        PatternMatchingExample.main(args);
        RecordPatternExample.main(args);
        SealedClassesExample.main(args);
        StructuredConcurrencyExample.main(args);
        ScopedValuesExample.main(args);
        // StringTemplatesExample.main(args); // This is not working, Because this feature has changed A LOT between versions.
    }
}