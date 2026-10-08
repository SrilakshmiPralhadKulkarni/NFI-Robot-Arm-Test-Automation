import java.util.ArrayList;
import java.util.List;

public class VerificationResult {

    private boolean passed = true;
    private List<String> failureMessages = new ArrayList<>();

    public void addFailure(String message) {
        passed = false;
        failureMessages.add(message);
    }

    public boolean isPassed() {
        return passed;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }
}