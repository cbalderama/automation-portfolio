package com.qa.automation.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static final String REPORT_DIR = "test-output/";
    private static final String REPORT_NAME = "extent-report.html";

    // ─── Initialize Report ───────────────────────────────────────────────────────

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static void createInstance() {
        String reportPath = REPORT_DIR + REPORT_NAME;

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        // Report configuration
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Cart Automation Test Report");
        sparkReporter.config().setReportName("Cart Happy Path Test Results");
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // System info shown in report
        extent.setSystemInfo("Project", "E-Commerce Cart Automation");
        extent.setSystemInfo("Environment", "Local");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Tester", "Cydrick Balderama");
        extent.setSystemInfo("Execution Time",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        System.out.println("✓ Extent Report initialized: " + reportPath);
    }

    // ─── Test Management ─────────────────────────────────────────────────────────

    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = getInstance().createTest(testName, description);
        testThread.set(test);
        return test;
    }

    public static ExtentTest createTest(String testName) {
        return createTest(testName, "");
    }

    public static ExtentTest getTest() {
        return testThread.get();
    }

    // ─── Flush Report ────────────────────────────────────────────────────────────

    public static void flush() {
        if (extent != null) {
            extent.flush();
            System.out.println("✓ Extent Report saved");
        }
    }
}