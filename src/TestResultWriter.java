import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TestResultWriter {

    public void write(
            String filePath,
            List<Point> expectedPoints,
            List<Point> actualPoints,
            VerificationResult verificationResult) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            writer.println("Expected visited points | Actual visited points | Test result");
            writer.println("-------------------------------------------------------------");

            int maxSize = Math.max(expectedPoints.size(), actualPoints.size());

            for (int i = 0; i < maxSize; i++) {

                String expected = i < expectedPoints.size()
                        ? expectedPoints.get(i).toString()
                        : "-";

                String actual = i < actualPoints.size()
                        ? actualPoints.get(i).toString()
                        : "-";

                String result = expected.equals(actual) ? "PASS" : "FAIL";

                writer.println(
                        expected + " | " + actual + " | " + result
                );
            }

            writer.println();
            writer.println(
                    "Overall result: "
                            + (verificationResult.isPassed() ? "PASS" : "FAIL")
            );
        }
    }
}