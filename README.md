# NFI Software Test Architect Assignment

This repository contains the solutions for the two QA Engineer assignments.

- **Assignment 1** — High-level System Test Plan and System Test Case Specification for a Home Security System
- **Assignment 2** — Java-based Robot Arm System Test Automation

---

# Assignment 1 — Home Security System Test Plan and Test Case Specification

The Assignment 1 deliverable is available in:

```text
assignment1_home_security/
```

The Excel workbook contains two tabs:

- **Test Plan** — high-level system test planning for the Home Security System
- **Test Case Specification** — detailed system test cases derived from the identified system requirements and system features

The purpose of this assignment is to demonstrate a structured system-test approach for an embedded Home Security System.

---

# Assignment 2 — Robot Arm System Test Automation

## Overview

Assignment 2 provides a Java-based test automation solution for validating the behavior of a robot arm operating within a rectangular work area.

The automation reads:

- a **system input file**, containing:
  - the rectangular working area
  - the points that the robot arm is expected to process
- a **system output file**, containing:
  - the points that were actually visited by the robot arm

The automation determines which input points are inside the allowed working area and compares them with the actual points reported by the system.

A `test_results.txt` file is generated with the verification result.

If a requirement is violated, the test fails and an appropriate failure message is printed to the console.

---

# Assignment 2 — Requirements Verified

The automation verifies the following behavior:

1. The system receives the expected points from the input file.
2. The robot arm visits only points that are inside the rectangular working area.
3. The actual visited points correspond to the expected valid points.
4. Points are visited in the expected order.
5. Invalid or malformed entries in the system output are detected.
6. The overall test result is `PASS` only when the required behavior is satisfied.

---

# Project Structure

```text
NFI_Assignment/
│
├── README.md
├── config.properties
│
├── assignment1_home_security/
│   └── Home_Security_System_Test_Plan_and_Test_Cases.xlsx
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

# Assignment 2 — Java Classes

## `Point.java`

Represents a coordinate used by the robot arm.

Each point contains:

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

Conceptually:

```text
minX <= x <= maxX
minY <= y <= maxY
```

Boundary points are considered valid working-area points.

---

## `SystemInput.java`

Stores the information parsed from the system input file.

It contains:

- the working-area `Rectangle`
- the complete list of requested `Point` objects

Points outside the rectangle are intentionally retained because the automation needs to verify that the system does not visit them.

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

The parser converts the input data into Java objects used by the verification logic.

---

## `OutputFileParser.java`

Reads the actual output produced by the system.

Valid coordinate entries are converted into `Point` objects.

Malformed entries such as:

```text
error
()
```

are captured separately so they can be reported as system-output failures.

The parser validates the format of the data.

Whether a point is allowed within the working area is checked by the verification logic.

---

## `VerificationResult.java`

Stores the result of the system verification.

It contains:

- the overall PASS/FAIL status
- the failure messages detected during verification

This allows all detected problems to be reported instead of stopping after the first failure.

---

## `RobotArmResultVerifier.java`

Contains the main system test verification logic.

It first determines the expected visited points by selecting only input points that are inside the rectangle.

It then verifies the actual system output.

Checks include:

- actual points are inside the working area
- expected and actual numbers of visited points match
- points are visited in the expected order
- expected and actual coordinates match
- invalid system-output entries are detected

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

The input file, actual system output file, and result file locations are kept outside the Java source code.

This allows another set of system input and output files to be tested without modifying the Java implementation.

---

## `RobotArmTestRunner.java`

This is the main executable class for Assignment 2.

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

The files used for an Assignment 2 test execution are configured in:

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

The input and output files can therefore have different names.

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

Example input:

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

- the system visits a point outside the rectangle
- an expected valid point is not visited
- an unexpected point is visited
- points are visited in the wrong order
- the number of actual and expected visited points differs
- the system output contains malformed data

Example console messages:

```text
FAIL: Visited point is outside the work area: (170, 150)

FAIL: Point mismatch at position 3.
Expected: (-4, -150)
Actual: (0, 0)

FAIL: Invalid output from system: error
```

---

# How to Run Assignment 2 in IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Make sure the Java source files under `src` are recognized as source files.
3. Verify that `config.properties` is located in the project root.
4. Verify that the paths configured in `config.properties` point to existing input and output files.
5. Open:

```text
RobotArmTestRunner.java
```

6. Run the `main()` method.

The IntelliJ working directory should be the project root.

Example:

```text
/Users/<user>/JavaProjects/NFI_Assignment
```

After execution:

- PASS/FAIL information is displayed in the IntelliJ console
- detailed results are written to:

```text
results/test_results.txt
```

---

# Design Approach

The Assignment 2 implementation intentionally keeps the test automation simple and easy to understand.

Responsibilities are separated into:

```text
Input parsing
Output parsing
System verification
Result reporting
Configuration
```

The test data and filenames are not hardcoded in the Java implementation.

This makes the automation reusable for:

- different rectangles
- different sets of input points
- different system-output files

while keeping the solution straightforward and maintainable.