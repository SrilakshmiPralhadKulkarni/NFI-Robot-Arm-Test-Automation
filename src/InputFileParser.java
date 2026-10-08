import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputFileParser {

    public SystemInput parse(String filePath) throws IOException {

        List<Point> rectanglePoints = new ArrayList<>();
        List<Point> points = new ArrayList<>();

        boolean readingRectangle = false;
        boolean readingPoints = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.equalsIgnoreCase("Rectangle")) {
                    readingRectangle = true;
                    readingPoints = false;
                    continue;
                }

                if (line.equalsIgnoreCase("Points")) {
                    readingRectangle = false;
                    readingPoints = true;
                    continue;
                }

                if (readingRectangle) {
                    rectanglePoints.addAll(parseRectanglePoints(line));
                } else if (readingPoints) {
                    points.add(parsePoint(line));
                }
            }
        }

        if (rectanglePoints.size() != 4) {
            throw new IllegalArgumentException(
                    "Input file must contain exactly 4 rectangle points."
            );
        }

        Rectangle rectangle = new Rectangle(
                rectanglePoints.get(0),
                rectanglePoints.get(1),
                rectanglePoints.get(2),
                rectanglePoints.get(3)
        );

        return new SystemInput(rectangle, points);
    }

    private List<Point> parseRectanglePoints(String line) {

        List<Point> points = new ArrayList<>();

        String[] pointStrings = line.split("\\),");

        for (String pointString : pointStrings) {

            pointString = pointString.replace("(", "")
                    .replace(")", "")
                    .trim();

            String[] values = pointString.split(",");

            if (values.length != 2) {
                throw new IllegalArgumentException(
                        "Invalid rectangle point format: " + pointString
                );
            }

            double x = Double.parseDouble(values[0].trim());
            double y = Double.parseDouble(values[1].trim());

            points.add(new Point(x, y));
        }

        return points;
    }

    private Point parsePoint(String line) {

        line = line.replace("(", "")
                .replace(")", "")
                .trim();

        String[] values = line.split(",");

        if (values.length != 2) {
            throw new IllegalArgumentException(
                    "Invalid point format: " + line
            );
        }

        double x = Double.parseDouble(values[0].trim());
        double y = Double.parseDouble(values[1].trim());

        return new Point(x, y);
    }
}