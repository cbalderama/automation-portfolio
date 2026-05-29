package com.qa.automation.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.automation.reports.ExtentReportManager;
import org.junit.jupiter.api.extension.*;

public class TestListener implements
        BeforeEachCallback,
        AfterEachCallback,
        TestWatcher {

    // ─── Before Each Test ────────────────────────────────────────────────────────

    @Override
    public void beforeEach(ExtensionContext context) {
        String testName = context.getDisplayName();
        String className = context.getTestClass()
                .map(Class::getSimpleName)
                .orElse("Unknown");

        ExtentTest test = ExtentReportManager.createTest(
                className + " - " + testName,
                "Automated test: " + testName
        );

        test.assignCategory(getTagsFromContext(context));
        System.out.println("✓ Extent test created: " + testName);
    }

    // ─── After Each Test ─────────────────────────────────────────────────────────

    @Override
    public void afterEach(ExtensionContext context) {
        // Report is flushed in CartHappyPathTest @AfterEach
        // after screenshot is taken
    }

    // ─── Test Watcher — Pass ─────────────────────────────────────────────────────

    @Override
    public void testSuccessful(ExtensionContext context) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test == null) return;
        test.log(Status.PASS, "✓ Test passed: " + context.getDisplayName());
        System.out.println("✓ Test marked as PASS in report");
    }

    // ─── Test Watcher — Fail ─────────────────────────────────────────────────────

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test == null) return;
        test.log(Status.FAIL, "✗ Test failed: " + cause.getMessage());
        test.log(Status.FAIL, "Stack trace: " + cause.toString());
        System.out.println("✗ Test marked as FAIL in report");
    }

    // ─── Test Watcher — Skip ─────────────────────────────────────────────────────

    @Override
    public void testDisabled(ExtensionContext context, java.util.Optional<String> reason) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test == null) return;
        test.log(Status.SKIP, "Test skipped: " + reason.orElse("No reason provided"));
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test == null) return;
        test.log(Status.SKIP, "Test aborted: " + cause.getMessage());
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────────

    private String[] getTagsFromContext(ExtensionContext context) {
        return context.getTags().toArray(new String[0]);
    }
}