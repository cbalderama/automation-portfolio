package com.qa.automation.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.automation.helpers.ScreenshotUtils;
import com.qa.automation.helpers.TestDataReader;
import com.qa.automation.listeners.TestListener;
import com.qa.automation.pages.CartPage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.ProductCatalogPage;
import com.qa.automation.pages.ProductDetailPage;
import com.qa.automation.reports.ExtentReportManager;
import com.qa.automation.utils.ConfigReader;
import com.qa.automation.utils.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListener.class)
public class CartHappyPathTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductCatalogPage catalogPage;
    private ProductDetailPage detailPage;
    private CartPage cartPage;

    // ─── Setup & Teardown ────────────────────────────────────────────────────────

    @BeforeEach
    public void setUp() {
        System.out.println("========== TEST SETUP ==========");

        driver = DriverManager.initializeDriver(ConfigReader.getBrowser());
        System.out.println("✓ WebDriver initialized");

        driver.get(ConfigReader.getAppUrl());
        System.out.println("✓ Navigated to: " + ConfigReader.getAppUrl());

        loginPage = new LoginPage(driver);
        catalogPage = new ProductCatalogPage(driver);
        detailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        System.out.println("✓ Page objects initialized");

        loginPage.login(
                ConfigReader.getTestEmail(),
                ConfigReader.getTestPassword()
        );
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        System.out.println("\n========== CLEANUP ==========");

        if (driver != null) {
            // Embed final screenshot in report
            String base64 = ScreenshotUtils.getScreenshotBase64(driver);
            if (base64 != null) {
                ExtentTest test = ExtentReportManager.getTest();
                if (test != null) {
                    test.addScreenCaptureFromBase64String(base64, "Final Screenshot");
                    System.out.println("✓ Final screenshot embedded in report");
                }
            }

            // Save ONE file to disk
            ScreenshotUtils.takeScreenshot(driver,
                    "FINAL_" + testInfo.getDisplayName().replace("()", ""));
        }

        DriverManager.quitDriver();
        System.out.println("✓ Browser closed");

        ExtentReportManager.flush();
        System.out.println("✓ Report saved");
    }

    // ─── Helper ──────────────────────────────────────────────────────────────────

    private void logStep(String message) {
        System.out.println(message);
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS, message);
        }
    }

    // ─── Test Cases ──────────────────────────────────────────────────────────────

    @Test
    @Tag("smoke")
    @Tag("cart")
    public void testAddProductToCart() {
        logStep("========== TC-CART-001: Add Product to Cart ==========");

        logStep("Step 1: Verify product catalog loaded...");
        catalogPage.verifyCatalogLoaded();

        logStep("Step 2: Click product '" + TestDataReader.getProductName() + "'...");
        catalogPage.clickProduct();

        logStep("Step 3: Verify product detail page opens with full information...");
        detailPage.verifyProductDetails();

        logStep("Step 4: Set quantity to " + TestDataReader.getProductQuantity() + "...");
        detailPage.incrementQuantity();

        logStep("Step 5: Verify quantity field updates to show '" + TestDataReader.getProductQuantity() + "'...");
        detailPage.verifyQuantityUpdated();

        logStep("Step 6: Click Add to Cart...");
        detailPage.clickAddToCart();

        logStep("Step 7: Verify success modal appears...");
        assertTrue(cartPage.isSuccessModalDisplayed(),
                "Success modal should be displayed after adding to cart");
        logStep("✓ Success modal displayed");

        logStep("Step 8: Click Shopping Cart button in modal...");
        cartPage.clickShoppingCart();
        logStep("✓ Navigated to shopping cart");

        logStep("Step 9: Verify cart displays added product with correct details...");
        cartPage.verifyCartContents();

        logStep("========== TC-CART-001 PASSED ✓ ==========");
    }
}