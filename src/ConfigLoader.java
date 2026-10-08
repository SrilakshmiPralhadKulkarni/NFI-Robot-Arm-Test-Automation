import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private Properties properties = new Properties();

    public ConfigLoader(String filePath) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        }
    }

    public String getInputFile() {
        return properties.getProperty("input.file");
    }

    public String getActualOutputFile() {
        return properties.getProperty("actual.output.file");
    }

    public String getTestResultFile() {
        return properties.getProperty("test.result.file");
    }
}