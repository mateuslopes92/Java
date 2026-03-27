package org.example.creational;

// Step 1: Abstract Products
interface Button {
    void render();
}

interface Checkbox {
    void render();
}

// Step 2: Concrete Products (Light Theme)
class LightButton implements Button {
    public void render() {
        System.out.println("Rendering Light Button");
    }
}

class LightCheckbox implements Checkbox {
    public void render() {
        System.out.println("Rendering Light Checkbox");
    }
}

// Step 2: Concrete Products (Dark Theme)
class DarkButton implements Button {
    public void render() {
        System.out.println("Rendering Dark Button");
    }
}

class DarkCheckbox implements Checkbox {
    public void render() {
        System.out.println("Rendering Dark Checkbox");
    }
}

// Step 3: Abstract Factory
interface UIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Step 4: Concrete Factories

class LightThemeFactory implements UIFactory {
    public Button createButton() {
        return new LightButton();
    }

    public Checkbox createCheckbox() {
        return new LightCheckbox();
    }
}

class DarkThemeFactory implements UIFactory {
    public Button createButton() {
        return new DarkButton();
    }

    public Checkbox createCheckbox() {
        return new DarkCheckbox();
    }
}

// Step 5: Factory Producer
class FactoryProducer {

    public static UIFactory getFactory(String theme) {

        if (theme.equalsIgnoreCase("light")) {
            return new LightThemeFactory();
        } else if (theme.equalsIgnoreCase("dark")) {
            return new DarkThemeFactory();
        }

        throw new IllegalArgumentException("Unknown theme");
    }
}

// Main
public class AbstractFactoryExample {

    public static void main(String[] args) {

        System.out.println("-------Abstract Factory (UI Theme)-------");

        // Get Light Theme Factory
        UIFactory lightFactory = FactoryProducer.getFactory("light");

        Button lightButton = lightFactory.createButton();
        Checkbox lightCheckbox = lightFactory.createCheckbox();

        lightButton.render();
        lightCheckbox.render();

        // Get Dark Theme Factory
        UIFactory darkFactory = FactoryProducer.getFactory("dark");

        Button darkButton = darkFactory.createButton();
        Checkbox darkCheckbox = darkFactory.createCheckbox();

        darkButton.render();
        darkCheckbox.render();

        System.out.println("-----------------------------------------\n");
    }
}