

package utils;

//============================================================================
//IMPORTS - These bring in the classes we need to use in this file
//============================================================================

import java.awt.Desktop;                  // Lets Java open files/browser on our computer
import java.io.File;                      // Represents a file or folder path
import java.io.IOException;               // Exception thrown when file/browser operations fail
import java.text.SimpleDateFormat;        // Formats a date into text (e.g. 2026.11.02.09.13.45)
import java.util.Date;                    // Gives us the current date and time
import java.util.List;                    // A list collection (used to hold group names)

import org.testng.ITestContext;           // Holds info about the whole <test> run (start/finish, parameters, groups)
import org.testng.ITestListener;          // Interface that lets us "listen" to test events (pass, fail, skip...)
import org.testng.ITestResult;            // Holds the result of ONE test method (name, status, exception...)

import com.aventstack.extentreports.ExtentReports;   // Main ExtentReports class - creates the report
import com.aventstack.extentreports.ExtentTest;      // Represents one test entry inside the report
import com.aventstack.extentreports.Status;          // PASS, FAIL, SKIP, INFO status values
import com.aventstack.extentreports.reporter.ExtentSparkReporter;      // Generates the HTML report file
import com.aventstack.extentreports.reporter.configuration.Theme;      // Lets us choose DARK or STANDARD theme
                 // Our own base class which has the captureScreen() method

import testCases.Basepage;


/*
* ============================================================================
* CLASS: ExtentReportManager
* ============================================================================
* PURPOSE:
*   This class creates a beautiful HTML report after our tests run.
*
* HOW IT WORKS:
*   "implements ITestListener" means this class LISTENS to TestNG events.
*   TestNG automatically calls our methods at the right moments:
*       - onStart()          -> before any test begins
*       - onTestSuccess()    -> when a test PASSES
*       - onTestFailure()    -> when a test FAILS
*       - onTestSkipped()    -> when a test is SKIPPED
*       - onFinish()         -> after all tests are completed
*
*   We never call these methods ourselves. TestNG calls them for us.
*
* HOW TO USE:
*   Add this class as a listener in testng.xml:
*       <listeners>
*           <listener class-name="utils.ExtentReportManager"/>
*       </listeners>
* ============================================================================
*/
public class ExtentReportManager implements ITestListener {

	// ------------------------------------------------------------------------
	// VARIABLES (shared by all methods in this class)
	// ------------------------------------------------------------------------

	// Decides HOW the report looks (title, theme, file location)
	public ExtentSparkReporter sparkReporter;

	// The main object - collects all test results and builds the report
	public ExtentReports extent;

	// Represents ONE test entry in the report (we create one per test result)
	public ExtentTest test;

	// Stores the report file name, e.g. Test-Report-2026.11.02.09.13.45.html
	String repName;


	// ========================================================================
	// METHOD 1: onStart()
	// Called ONCE, BEFORE the test run begins.
	// Here we set up and configure the report.
	// ========================================================================
	public void onStart(ITestContext testContext) {

		// STEP 1: Create a timestamp (current date and time) as text.
		// Why? So every report gets a UNIQUE name and old reports are not overwritten.
		// Example result: 2026.11.02.09.13.45
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

		// STEP 2: Build the report file name using the timestamp.
		// Example: Test-Report-2026.11.02.09.13.45.html
		repName = "Test-Report-" + timeStamp + ".html";

		// STEP 3: Tell the reporter WHERE to save the file.
		// ".\\reports\\" means: the "reports" folder inside our project.
		// (\\ is used because a single \ is a special character in Java strings)
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

		// STEP 4: Customize how the report looks
		sparkReporter.config().setDocumentTitle("opencart Automation Report"); // Title shown in the browser tab
		sparkReporter.config().setReportName("opencart Functional Testing");   // Heading shown inside the report
		sparkReporter.config().setTheme(Theme.DARK);                           // Dark background (use Theme.STANDARD for light)

		// STEP 5: Create the main ExtentReports object
		extent = new ExtentReports();

		// STEP 6: Connect the reporter to it.
		// Meaning: "Use sparkReporter to write the results into the HTML file."
		extent.attachReporter(sparkReporter);

		// STEP 7: Add system information. This appears in the "Environment" section of the report.
		// Format: setSystemInfo("Label", "Value")
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name")); // Gets the logged-in computer user name automatically
		extent.setSystemInfo("Environment", "QA");

		// STEP 8: Read the "os" parameter from testng.xml.
		// In testng.xml we write:  <parameter name="os" value="Windows"/>
		String os = testContext.getCurrentXmlTest().getParameter("os");
		if (os != null) {                                   // Only add it if the parameter actually exists
			extent.setSystemInfo("Operating System", os);
		}

		// STEP 9: Read the "browser" parameter from testng.xml.
		// In testng.xml we write:  <parameter name="browser" value="chrome"/>
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		if (browser != null) {                              // Only add it if the parameter actually exists
			extent.setSystemInfo("Browser", browser);
		}

		// STEP 10: Find out which GROUPS are included in this run (sanity, regression, master...).
		// These come from the <groups><run><include name="..."/> part of testng.xml.
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {                    // Only add if at least one group is included
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}


	// ========================================================================
	// METHOD 2: onTestSuccess()
	// Called automatically EVERY TIME a test method PASSES.
	// ========================================================================
	public void onTestSuccess(ITestResult result) {

		// Create a new entry in the report. The entry name is the test CLASS name.
		// Example: tests.TC001_AccountRegistrationTest
		test = extent.createTest(result.getTestClass().getName());

		// Add the test's groups as "categories" in the report (sanity, regression, etc.)
		// This lets us filter the report by group.
		test.assignCategory(result.getMethod().getGroups());

		// Write a green PASS message in the report.
		// result.getName() gives the test METHOD name.
		test.log(Status.PASS, result.getName() + " got successfully executed");
	}


	// ========================================================================
	// METHOD 3: onTestFailure()
	// Called automatically EVERY TIME a test method FAILS.
	// Besides logging, we also attach a SCREENSHOT of the failure.
	// ========================================================================
	public void onTestFailure(ITestResult result) {

		// Create a new entry in the report (named after the test class)
		test = extent.createTest(result.getTestClass().getName());

		// Add groups as categories
		test.assignCategory(result.getMethod().getGroups());

		// Write a red FAIL message with the test method name
		test.log(Status.FAIL, result.getName() + " got failed");

		// Write the REASON for failure (the exception/error message).
		// String.valueOf() safely converts the error to text.
		test.log(Status.INFO, String.valueOf(result.getThrowable()));

		// Now try to take a screenshot.
		// We use try-catch because screenshot capture can fail,
		// and we don't want that to crash the whole report.
		try {
			// result.getInstance() gives the test class object that is currently running.
			// We convert (cast) it to Basepage so we can use its captureScreen() method.
			// This way we always use the SAME browser (driver) that the failed test used.
			Basepage base = (Basepage) result.getInstance();

			// Take the screenshot. It is saved as an image file, and the file path is returned.
			String imgPath = base.captureScreen(result.getName());

			// Attach that screenshot to the report so we can see it next to the failure.
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			// If anything goes wrong, print the error in the console
			e.printStackTrace();
		}
	}


	// ========================================================================
	// METHOD 4: onTestSkipped()
	// Called automatically EVERY TIME a test method is SKIPPED.
	// (Example: a test is skipped when another test it depends on has failed.)
	// ========================================================================
	public void onTestSkipped(ITestResult result) {

		// Create a new entry in the report
		test = extent.createTest(result.getTestClass().getName());

		// Add groups as categories
		test.assignCategory(result.getMethod().getGroups());

		// Write a SKIP (orange/yellow) message with the test method name
		test.log(Status.SKIP, result.getName() + " got skipped");

		// Write the reason why it was skipped
		test.log(Status.INFO, String.valueOf(result.getThrowable()));
	}


	// ========================================================================
	// METHOD 5: onFinish()
	// Called ONCE, AFTER all tests have finished running.
	// Here we save the report and open it automatically in the browser.
	// ========================================================================
	public void onFinish(ITestContext testContext) {

		// VERY IMPORTANT: flush() writes everything collected so far into the HTML file.
		// Without this line, the report file stays empty!
		extent.flush();

		// Build the full path of the report file.
		// System.getProperty("user.dir") = the current project folder path.
		// Example: C:\Projects\opencart\reports\Test-Report-2026.11.02.09.13.45.html
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;

		// Create a File object pointing to the report
		File extentReport = new File(pathOfExtentReport);

		// Open the report automatically in the default browser
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			// If the browser cannot be opened, print the error in the console
			e.printStackTrace();
		}
	}
}