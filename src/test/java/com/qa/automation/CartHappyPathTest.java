package com.qa.automation.tests;

import com.qa.automation.pages.CartPage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.ProductCatalogPage;
import com.qa.automation.pages.ProductDetailPage;
import com.qa.automation.utils.DriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

public class CartHappyPathTest {
    static WebDriver driver;
    static LoginPage loginPage;
    static ProductCatalogPage catalogPage;
    static ProductDetailPage detailPage;
    static CartPage cartPage;

    @BeforeAll
    public static void setUp() {
        System.out.println("========== TEST SETUP ==========");
        driver = DriverManager.initializeDriver("chrome");
        System.out.println("✓ WebDriver initialized");

        driver.get("http://localhost:8081");
        System.out.println("✓ Navigated to application");

        loginPage = new LoginPage(driver);
        loginPage.login("test@email.com", "password");
        System.out.println("✓ User logged in");

        catalogPage = new ProductCatalogPage(driver);
        detailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        System.out.println("✓ Page objects initialized");
    }

    @Test
    public void testAddProductToCart() {
        System.out.println("\n========== TEST EXECUTION ==========");

        System.out.println("Step 1: Verify product catalog loaded with all products...");
        catalogPage.verifyCatalogLoaded();

        System.out.println("Step 2: Click product 'Lenovo ThinkPad X1 Carbon'...");
        catalogPage.clickProduct();

        System.out.println("Step 2.5: Verify product detail page opens with full information...");
        detailPage.verifyProductDetails();

        System.out.println("Step 3: Set quantity to 2...");
        detailPage.incrementQuantity();

        System.out.println("Step 3.5: Verify quantity field updates to show '2'...");
        detailPage.verifyQuantityUpdated();

        System.out.println("Step 4: Click Add to Cart...");
        detailPage.clickAddToCart();

        System.out.println("Step 5: Verify success modal appears...");
        assertTrue(cartPage.isSuccessModalDisplayed(), "Success modal should be displayed");
        System.out.println("✓ Success modal displayed: 'Added to cart.'");

        System.out.println("Step 6: Click Shopping Cart button in modal...");
        cartPage.clickShoppingCart();
        System.out.println("✓ Navigated to shopping cart");

        // DEBUG — check what's actually on the cart page
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("DEBUG: Current URL = " + driver.getCurrentUrl());
        String pageSource = driver.getPageSource();
        System.out.println("DEBUG: Page contains 'Lenovo': " + pageSource.contains("Lenovo"));
        System.out.println("DEBUG: Page contains 'Your cart is empty': " + pageSource.contains("Your cart is empty"));
        System.out.println("DEBUG: Page contains 'Start Shopping': " + pageSource.contains("Start Shopping"));

        System.out.println("Step 7: Verify cart displays added product with correct details...");
        cartPage.verifyCartContents();

        System.out.println("\n========== TEST PASSED ✓ ==========");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("\n========== CLEANUP ==========");
        DriverManager.quitDriver();
        System.out.println("✓ Browser closed");
    }
}