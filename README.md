# Robot Arm System Test Automation

## Overview

This project provides a simple Java-based test automation solution for validating the behavior of a robot arm operating within a rectangular work area.

The test automation reads:

- A **system input file** containing:
    - The rectangular working area
    - The points that the robot arm is expected to process
- A **system output file** containing:
    - The points that were actually visited by the robot arm

The automation determines which input points are within the allowed working area and compares them with the actual points reported by the system.

A `test_results.txt` file is generated with the verification result.

If any requirement is violated, the test fails and an appropriate failure message is printed to the console.

---

# System Requirements Verified

The automation verifies the following system behavior:

1. The system receives the expected points from the input file.
2. The robot arm visits only points that are within the rectangular working area.
3. The actual visited points correspond to the expected valid points and are visited in the expected order.
4. Invalid or malformed entries in the system output are detected.
5. The overall test result is `PASS` only when all required behavior is satisfied.

---

# Project Structure

```text
NFI_Assignment/
│
├── config.properties
│
├── src/
│   ├── Point.java
│   ├── Rectangle.java
│   ├── SystemInput.java
│   ├── InputFileParser.java
│   ├── OutputFileParser.java
│   ├── VerificationResult.java
│   ├── RobotArmResultVerifier.java
│   ├── TestResultWriter.java
│   ├── ConfigLoader.java
│   └── RobotArmTestRunner.java
│
├── test-data/
│   ├── input/
│   │   └── system_input_file.1630412935.txt
│   │
│   └── actual-output/
│       └── system_output_file.1630412935.txt
│
└── results/
    └── test_results.txt
```

---

# Java Classes

## `Point.java`

Represents a coordinate used by the robot arm.

A point contains:

```text
x coordinate
y coordinate
```

Example:

```text
(-3, -149)
```

---

## `Rectangle.java`

Represents the robot arm working area.

The rectangle boundaries are calculated from the four rectangle points provided in the input file.

It also provides the check used to determine whether a point is inside or on the boundary of the working area.

For example:

```text
minX <= x <= maxX
minY <= y <= maxY
```

Boundary points are considered valid working-area points.

---

## `SystemInput.java`

Stores the information parsed from the system input file.

It contains:

- The working-area `Rectangle`
- The complete list of requested `Point` objects

Points outside the rectangle are intentionally retained because the test needs to verify that the system does not visit them.

---

## `InputFileParser.java`

Reads and parses the system input file.

It extracts:

```text
Rectangle
(x1, y1), (x2, y2), (x3, y3), (x4, y4)

Points
(x, y)
(x, y)
...
```

The parser converts the input data into Java objects that can be used by the verification logic.

---

## `OutputFileParser.java`

Reads the actual output produced by the system.

Valid coordinate entries are converted into `Point` objects.

Malformed entries such as:

```text
error
()
```

are captured separately so that they can be reported as system-output failures.

The parser only validates the format of the data.

Whether a point is allowed inside the working area is checked by the verification logic.

---

## `VerificationResult.java`

Stores the result of the system verification.

It contains:

- Overall PASS/FAIL status
- Failure messages detected during verification

This allows all detected problems to be reported instead of stopping after the first failure.

---

## `RobotArmResultVerifier.java`

Contains the main system test verification logic.

It first determines the expected visited points by selecting only input points that are inside the rectangle.

It then verifies the actual system output.

Checks include:

- Actual points are inside the working area
- Expected and actual numbers of visited points match
- Points are visited in the expected order
- Expected and actual coordinates match
- Invalid system-output entries are detected

Any violation results in a test failure.

---

## `TestResultWriter.java`

Creates:

```text
results/test_results.txt
```

The result file contains the expected points, actual points, and the corresponding PASS/FAIL result.

Example:

```text
Expected visited points | Actual visited points | Test result
-------------------------------------------------------------
(-3, -149)              | (-3, -149)            | PASS
(4, 150)                | (4, 150)              | PASS

Overall result: PASS
```

---

## `ConfigLoader.java`

Loads the test configuration from:

```text
config.properties
```

The input file, actual system output file, and result file locations are therefore kept outside the Java source code.

This allows another set of system input and output files to be tested without modifying or recompiling the Java implementation.

---

## `RobotArmTestRunner.java`

This is the main executable class for the test automation.

It coordinates the complete test flow:

```text
Load configuration
        ↓
Read system input
        ↓
Read actual system output
        ↓
Determine expected visited points
        ↓
Verify system behavior
        ↓
Generate test_results.txt
        ↓
Print PASS/FAIL information to console
```

Run this class to execute the system test.

---

# Configuration

The files used for a test execution are configured in:

```text
config.properties
```

Example:

```properties
input.file=test-data/input/system_input_file.1630412935.txt
actual.output.file=test-data/actual-output/system_output_file.1630412935.txt
test.result.file=results/test_results.txt
```

The Java source code does not contain assumptions about the actual filename.

Therefore the input and output files may have different names.

---

# Running With Different Input and Output Files

To test another system input/output pair, place the files in the appropriate folders.

For example:

```text
test-data/input/pass_input.txt

test-data/actual-output/pass_output.txt
```

Then update only `config.properties`:

```properties
input.file=test-data/input/pass_input.txt
actual.output.file=test-data/actual-output/pass_output.txt
test.result.file=results/test_results.txt
```

No Java source-code changes are required.

The same automation will parse the new rectangle and points and perform the verification.

---

# Example PASS Scenario

Input:

```text
Rectangle
(0, 0), (0, 100), (100, 0), (100, 100)

Points
(10, 10)
(50, 50)
(120, 50)
(100, 100)
(-5, 20)
(0, 0)
```

The expected visited points are:

```text
(10, 10)
(50, 50)
(100, 100)
(0, 0)
```

The points:

```text
(120, 50)
(-5, 20)
```

are outside the working area and therefore must not be visited.

A valid system output would be:

```text
(10, 10)
(50, 50)
(100, 100)
(0, 0)
```

This results in:

```text
Overall result: PASS
```

---

# Example Failure Conditions

The automation reports a failure when, for example:

- The system visits a point outside the rectangle
- An expected valid point is not visited
- An unexpected point is visited
- Points are visited in the wrong order
- The number of actual and expected visited points differs
- The system output contains malformed data

Example console messages:

```text
FAIL: Visited point is outside the work area: (170, 150)

FAIL: Point mismatch at position 3.
Expected: (-4, -150)
Actual: (0, 0)

FAIL: Invalid output from system: error
```

---

# How to Run in IntelliJ IDEA

1. Open the project in IntelliJ IDEA.

2. Make sure the Java source files under `src` are recognized as source files.

3. Verify that `config.properties` is located in the project root.

4. Verify that the paths configured in `config.properties` point to existing files.

5. Open:

```text
RobotArmTestRunner.java
```

6. Run the `main()` method.

The IntelliJ working directory should be the project root, for example:

```text
/Users/<user>/JavaProjects/NFI_Assignment
```

After execution:

- PASS/FAIL information is displayed in the IntelliJ console.
- Detailed results are written to:

```text
results/test_results.txt
```

---

# Design Approach

The implementation intentionally keeps the test automation simple.

Responsibilities are separated into:

```text
Input parsing
Output parsing
System verification
Result reporting
Configuration
```

The test data and filenames are not hardcoded in the Java implementation.

This makes the automation reusable for different rectangles, different sets of points, and different system-output files while keeping the solution easy to understand and maintain.