import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OutputFileParser {

    private List<String> invalidLines = new ArrayList<>();

    public List<Point> parse(String filePath) throws IOException {

        List<Point> actualPoints = new ArrayList<>();
        invalidLines.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    Point point = parsePoint(line);
                    actualPoints.add(point);

                } catch (IllegalArgumentException e) {
                    invalidLines.add(line);
                }
            }
        }

        return actualPoints;
    }

    private Point parsePoint(String line) {

        if (!line.startsWith("(") || !line.endsWith(")")) {
            throw new IllegalArgumentException("Invalid point format");
        }

        String content = line.substring(1, line.length() - 1).trim();

        if (content.isEmpty()) {
            throw new IllegalArgumentException("Empty point");
        }

        String[] values = content.split(",");

        if (values.length != 2) {
            throw new IllegalArgumentException("Invalid point format");
        }

        try {
            double x = Double.parseDouble(values[0].trim());
            double y = Double.parseDouble(values[1].trim());

            return new Point(x, y);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid coordinate");
        }
    }

    public List<String> getInvalidLines() {
        return invalidLines;
    }
}