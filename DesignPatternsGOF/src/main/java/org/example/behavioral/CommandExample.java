package org.example.behavioral;

// Step 1: Command interface
interface Command {
    void execute();
}

// Step 2: Receiver (actual logic)
class Light {

    public void turnOn() {
        System.out.println("Light is ON");
    }

    public void turnOff() {
        System.out.println("Light is OFF");
    }
}

// Step 3: Concrete Commands

class TurnOnCommand implements Command {

    private final Light light;

    public TurnOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }
}

class TurnOffCommand implements Command {

    private final Light light;

    public TurnOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }
}

// Step 4: Invoker
class RemoteControl {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        if (command == null) {
            throw new IllegalStateException("No command set");
        }
        command.execute();
    }
}

// Main
public class CommandExample {

    public static void main(String[] args) {

        System.out.println("-------Command-------");

        Light light = new Light();

        Command turnOn = new TurnOnCommand(light);
        Command turnOff = new TurnOffCommand(light);

        RemoteControl remote = new RemoteControl();

        // Turn ON
        remote.setCommand(turnOn);
        remote.pressButton();

        // Turn OFF
        remote.setCommand(turnOff);
        remote.pressButton();

        System.out.println("---------------------\n");
    }
}