package reqres.config;
import amazon.utils.files.PropertyReader;

public class ApiConfig {
    private static final PropertyReader propertyReader = new PropertyReader("./src/main/resources/API-Configs.properties");
    public static final String BASE_URL = propertyReader.getProperty("BASE_URL");
    public static final String USERS_ENDPOINT =  propertyReader.getProperty("USERS_ENDPOINT");
    public static final int DEFAULT_TIMEOUT = Integer.parseInt(propertyReader.getProperty("DEFAULT_TIMEOUT"));
}
