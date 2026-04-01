package org.example.creational;

import java.util.HashMap;
import java.util.Map;

// Step 1: Prototype abstract class
abstract class Document implements Cloneable {

    protected String title;
    protected String content;

    abstract void show();

    // Clone method
    public Document clone() {
        try {
            return (Document) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported");
        }
    }
}

// Step 2: Concrete classes

class Report extends Document {

    public Report() {
        this.title = "Report Template";
        this.content = "Report Content...";
    }

    @Override
    void show() {
        System.out.println("Report Title: " + title);
        System.out.println("Report Content: " + content);
    }
}

class Invoice extends Document {

    public Invoice() {
        this.title = "Invoice Template";
        this.content = "Invoice Content...";
    }

    @Override
    void show() {
        System.out.println("Invoice Title: " + title);
        System.out.println("Invoice Content: " + content);
    }
}

// Step 3: Prototype Registry (cache)

class DocumentCache {

    private static final Map<String, Document> cache = new HashMap<>();

    // Load templates
    public static void loadCache() {
        cache.put("report", new Report());
        cache.put("invoice", new Invoice());
    }

    // Return cloned document
    public static Document getDocument(String type) {
        Document doc = cache.get(type);
        return doc.clone();
    }
}

// Main
public class PrototypeExample {

    public static void main(String[] args) {

        System.out.println("-------Prototype-------");

        // Load templates
        DocumentCache.loadCache();

        // Clone documents instead of creating from scratch
        Document doc1 = DocumentCache.getDocument("report");
        Document doc2 = DocumentCache.getDocument("report");
        Document doc3 = DocumentCache.getDocument("invoice");

        doc1.show();
        doc2.show();
        doc3.show();

        // Show they are different instances
        System.out.println("Hashcode doc1: " + doc1.hashCode());
        System.out.println("Hashcode doc2: " + doc2.hashCode());
        System.out.println("Hashcode doc3: " + doc3.hashCode());

        System.out.println("-----------------------\n");
    }
}