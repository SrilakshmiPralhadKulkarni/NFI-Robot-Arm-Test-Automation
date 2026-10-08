import java.util.List;

public class RobotArmTestRunner {

    public static void main(String[] args) {

        try {
            ConfigLoader config = new ConfigLoader("config.properties");

            String inputFile = config.getInputFile();
            String outputFile = config.getActualOutputFile();
            String resultFile = config.getTestResultFile();

            InputFileParser inputParser = new InputFileParser();
            SystemInput systemInput = inputParser.parse(inputFile);

            OutputFileParser outputParser = new OutputFileParser();
            List<Point> actualPoints = outputParser.parse(outputFile);

            RobotArmResultVerifier verifier = new RobotArmResultVerifier();

            VerificationResult verificationResult = verifier.verify(
                    systemInput,
                    actualPoints,
                    outputParser.getInvalidLines()
            );

            List<Point> expectedPoints =
                    verifier.getExpectedPoints(systemInput);

            TestResultWriter resultWriter = new TestResultWriter();

            resultWriter.write(
                    resultFile,
                    expectedPoints,
                    actualPoints,
                    verificationResult
            );

            if (verificationResult.isPassed()) {
                System.out.println("Test result: PASS");
            } else {
                System.out.println("Test result: FAIL");

                for (String failure :
                        verificationResult.getFailureMessages()) {

                    System.out.println("FAIL: " + failure);
                }
            }

            System.out.println(
                    "Test results written to: " + resultFile
            );

        } catch (Exception e) {
            System.out.println(
                    "Test execution failed: " + e.getMessage()
            );
        }
    }
}