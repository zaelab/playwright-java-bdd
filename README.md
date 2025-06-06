# Playwright Java Cucumber Project

## Prerequisites
- Java 11 or higher
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
- **Sequential Mode:**
  ```bash
  mvn clean test -P sequential
  ```

- **Parallel Mode:**
  ```bash
  mvn clean test -P parallel
  ```

## Generating Reports
- **Allure Report:**
  - Raw results are stored in: `allure-results/`
    - Contains test execution data, screenshots, and other attachments
    - Required for generating Allure reports
    - Can be safely deleted before a new test run
  - To view the report:
    ```bash
    mvn allure:serve
    ```
  - To generate a static report:
    ```bash
    mvn allure:report
    ```
    The static report will be generated in: `target/site/allure-maven-plugin`

- **Cucumber HTML Report:**
  After running tests, the report is generated at `target/cucumber-reports/cucumber-pretty.html`.

- **Cucumber JSON Report:**
  The JSON report is generated at `target/cucumber-reports/cucumber.json`.

## Project Structure
- `src/main/java/zaelab/driver/DriverBase.java`: Manages browser instances using ThreadLocal for thread safety.
- `src/test/java/runners/RunCucumberTest.java`: Cucumber runner configuration.
- `src/test/resources/features/`: Contains Cucumber feature files.
- `src/test/java/steps/`: Contains step definitions.
- `src/test/java/hooks/`: Contains hooks for test setup and teardown.
- `allure-results/`: Contains raw test execution data for Allure reports.

## Troubleshooting
- If you encounter browser-related issues, ensure Playwright browsers are installed correctly.
- For parallel execution issues, check `junit-platform.properties` and ensure `ThreadLocal` is used for browser management.
- If Allure reports are not generating, ensure the `allure-results` directory exists and contains test execution data.

## License
This project is licensed under the MIT License.

# Allure Report Usage

## How to View Allure Reports

After running your tests and generating the Allure report, you have two main options to view it:

### 1. Recommended: Use Allure's Built-in Server

Run:
```bash
mvn allure:serve
```
This will start a local web server and automatically open the report in your browser. This is the most reliable way to view the report locally.

### 2. Use a Simple HTTP Server (for static report)
If you want to view the static report generated in `target/allure-report`, you must serve it via a local web server. For example:
```bash
cd target/allure-report
python3 -m http.server 8080
```
Then open [http://localhost:8080](http://localhost:8080) in your browser.

### 3. Do NOT open index.html directly
Opening `index.html` directly with `file://` in your browser will not work correctly. The report will show only "Loading..." because modern browsers block AJAX/JavaScript requests for local files due to security restrictions (CORS).

### 4. Sharing the Report
To share the report, upload the entire `allure-report` directory to a web server or use a static file host (such as GitHub Pages, Netlify, etc.).

---

**Summary:**
- Use `mvn allure:serve` for local viewing.
- Use a local web server for the static report.
- Never open `index.html` directly with `file://`. 