import java.util.ArrayList;
import java.util.List;

public class RobotArmResultVerifier {

    public VerificationResult verify(
            SystemInput systemInput,
            List<Point> actualPoints,
            List<String> invalidOutputLines) {

        VerificationResult result = new VerificationResult();

        List<Point> expectedPoints = getExpectedPoints(systemInput);

        // Requirement 1:
        // System should visit only points that are inside the rectangle
        for (Point actualPoint : actualPoints) {

            if (!systemInput.getRectangle().contains(actualPoint)) {
                result.addFailure(
                        "Visited point is outside the work area: " + actualPoint
                );
            }
        }

        // Invalid/malformed output from the system
        for (String invalidLine : invalidOutputLines) {
            result.addFailure(
                    "Invalid output from system: " + invalidLine
            );
        }

        // Requirement 2 and 3:
        // Actual visited points should match expected valid points
        // in the same order
        if (expectedPoints.size() != actualPoints.size()) {

            result.addFailure(
                    "Number of visited points does not match. Expected: "
                            + expectedPoints.size()
                            + ", Actual: "
                            + actualPoints.size()
            );
        }

        int pointsToCompare =
                Math.min(expectedPoints.size(), actualPoints.size());

        for (int i = 0; i < pointsToCompare; i++) {

            Point expected = expectedPoints.get(i);
            Point actual = actualPoints.get(i);

            if (!samePoint(expected, actual)) {

                result.addFailure(
                        "Point mismatch at position "
                                + (i + 1)
                                + ". Expected: "
                                + expected
                                + ", Actual: "
                                + actual
                );
            }
        }

        return result;
    }

    public List<Point> getExpectedPoints(SystemInput systemInput) {

        List<Point> expectedPoints = new ArrayList<>();

        for (Point point : systemInput.getPoints()) {

            if (systemInput.getRectangle().contains(point)) {
                expectedPoints.add(point);
            }
        }

        return expectedPoints;
    }

    private boolean samePoint(Point expected, Point actual) {

        return Double.compare(expected.getX(), actual.getX()) == 0
                && Double.compare(expected.getY(), actual.getY()) == 0;
    }
}