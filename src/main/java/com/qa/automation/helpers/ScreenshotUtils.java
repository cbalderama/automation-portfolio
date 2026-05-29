package com.qa.automation.helpers;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "test-output/screenshots/";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    // ─── Take Screenshot ─────────────────────────────────────────────────────────

    /**
     * Takes a screenshot and saves it to test-output/screenshots/
     * Returns the file path for embedding in reports.
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            // Create screenshots directory if it doesn't exist
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            // Generate filename with timestamp
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;

            // Take and save screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(filePath));

            System.out.println("✓ Screenshot saved: " + filePath);
            return filePath;

        } catch (IOException e) {
            System.err.println("✗ Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Takes a screenshot on test failure.
     */
    public static String takeFailureScreenshot(WebDriver driver, String testName) {
        return takeScreenshot(driver, "FAIL_" + testName);
    }

    /**
     * Takes a screenshot on test pass.
     */
    public static String takePassScreenshot(WebDriver driver, String testName) {
        return takeScreenshot(driver, "PASS_" + testName);
    }

    // ─── Get Screenshot as Base64 ────────────────────────────────────────────────

    /**
     * Returns screenshot as Base64 string for embedding directly in HTML reports.
     */
    public static String getScreenshotBase64(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.err.println("✗ Failed to get base64 screenshot: " + e.getMessage());
            return null;
        }
    }
}