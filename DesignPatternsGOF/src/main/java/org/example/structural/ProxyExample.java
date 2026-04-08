package org.example.structural;

// Step 1: Subject interface
interface FileService {
    void readFile(String filename);
}

// Step 2: Real Subject (actual object)
class RealFileService implements FileService {

    @Override
    public void readFile(String filename) {
        System.out.println("Reading file: " + filename);
    }
}

// Step 3: Proxy (controls access)
class FileServiceProxy implements FileService {

    private final RealFileService realService;
    private final String userRole;

    public FileServiceProxy(String userRole) {
        this.realService = new RealFileService();
        this.userRole = userRole;
    }

    @Override
    public void readFile(String filename) {

        // Access control
        if ("ADMIN".equalsIgnoreCase(userRole)) {
            realService.readFile(filename);
        } else {
            System.out.println("Access denied. Only ADMIN can read files.");
        }
    }
}

// Main
public class ProxyExample {

    public static void main(String[] args) {

        System.out.println("-------Proxy-------");

        // User with no permission
        FileService userService = new FileServiceProxy("USER");
        userService.readFile("data.txt");

        System.out.println();

        // Admin user
        FileService adminService = new FileServiceProxy("ADMIN");
        adminService.readFile("data.txt");

        System.out.println("-------------------\n");
    }
}