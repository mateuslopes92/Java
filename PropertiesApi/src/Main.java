public class Main {
    public static void main(String[] args) throws Exception {
        CreatePropertiesFile propertiesFile = new CreatePropertiesFile();

        propertiesFile.createInfoPropertiesFile();

        ReadingPropertiesFromFile infoProps = new ReadingPropertiesFromFile();

        System.out.println(infoProps.properties().getProperty("name"));
        System.out.println(infoProps.properties().getProperty("email"));
    }
}
