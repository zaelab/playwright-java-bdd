# Playwright Java Cucumber Project

## Prerequisites
- Java 18 or higher
- Maven 3.6 or higher
- Node.js 14 or higher (for Playwright)
- Git

## Setup Steps
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd playwright_java
   ```

2. Install Playwright browsers:
   ```bash
   mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

## Running Tests

### Sequential Execution
To run tests sequentially (one at a time):
```bash
mvn clean verify -P sequential
```

### Parallel Execution
To run tests in parallel (2 threads by default):
```bash
mvn clean verify -P parallel
```

### Running Specific Tests
To run tests with specific tags:
```bash
# Run tests with @smoke tag
mvn clean verify -Dcucumber.filter.tags="@smoke"

# Run tests excluding @ignore tag
mvn clean verify -Dcucumber.filter.tags="not @ignore"

# Run tests with multiple tags
mvn clean verify -Dcucumber.filter.tags="@smoke and not @ignore"
```

## Test Reports

### Serenity Reports
Serenity generates comprehensive test reports that include:
- Test execution results
- Step-by-step test execution details
- Screenshots of failed steps
- Test duration and statistics
- Requirements coverage

To view the Serenity report:
1. After test execution, navigate to: `target/site/serenity/index.html`
2. Open the file in your web browser

### Cucumber Reports
The project generates two types of Cucumber reports:

1. HTML Report (Pretty Format):
   - Location: `target/cucumber-reports/cucumber-pretty.html`
   - Contains detailed step-by-step execution in a readable format

2. JSON Report:
   - Location: `target/cucumber-reports/cucumber.json`
   - Can be used to generate additional custom reports

3. Timeline Report:
   - Location: `target/cucumber-reports/timeline`
   - Shows test execution timeline and parallel execution details

### Screenshots
- Failed test screenshots are saved in: `target/serenity-screenshots/`
- Each screenshot is named with the scenario name and timestamp
- Screenshots are automatically included in the Serenity report

## Project Structure
```
src/
├── main/
│   └── java/
│       └── zaelab/
│           ├── driver/
│           │   └── DriverBase.java    # Browser management
│           └── utilities/
│               └── file/
│                   └── ReadPropertyFile.java
└── test/
    ├── java/
    │   ├── hooks/
    │   │   └── TestHooks.java        # Test setup and teardown
    │   ├── runners/
    │   │   └── RunCucumberTest.java  # Test runner configuration
    │   └── steps/
    │       └── LoginSteps.java       # Step definitions
    └── resources/
        ├── features/                  # Cucumber feature files
        └── config.properties          # Configuration file
```

## Configuration
- Browser settings can be modified in `src/main/resources/config.properties`
- Test execution settings can be modified in `pom.xml`
- Parallel execution settings can be adjusted in the `parallel` profile in `pom.xml`

## Troubleshooting

### Common Issues
1. **Browser Installation Issues**
   - Ensure you have run the Playwright browser installation command
   - Check your Node.js version is compatible

2. **Test Execution Issues**
   - Verify Java version is 18 or higher
   - Check Maven version is 3.6 or higher
   - Ensure all dependencies are downloaded

3. **Report Generation Issues**
   - Clean the project: `mvn clean`
   - Ensure test execution completed successfully
   - Check write permissions in the target directory

### Getting Help
- Check the test execution logs in the console output
- Review the Serenity report for detailed test execution information
- Check the Cucumber reports for step-by-step execution details

## License
This project is licensed under the MIT License. 