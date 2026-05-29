package com.qa.automation.tests;

import com.qa.automation.pages.CartPage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.ProductCatalogPage;
import com.qa.automation.pages.ProductDetailPage;
import com.qa.automation.utils.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

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

        driver = DriverManager.initializeDriver("chrome");
        System.out.println("✓ WebDriver initialized");

        driver.get("http://localhost:8081");
        System.out.println("✓ Navigated to application");

        loginPage = new LoginPage(driver);
        catalogPage = new ProductCatalogPage(driver);
        detailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        System.out.println("✓ Page objects initialized");

        loginPage.login("test@email.com", "password");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("\n========== CLEANUP ==========");
        DriverManager.quitDriver();
        System.out.println("✓ Browser closed");
    }

    // ─── Test Cases ──────────────────────────────────────────────────────────────

    @Test
    @Tag("smoke")
    @Tag("cart")
    public void testAddProductToCart() {
        System.out.println("\n========== TC-CART-001: Add Product to Cart ==========");

        System.out.println("Step 1: Verify product catalog loaded...");
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
        assertTrue(cartPage.isSuccessModalDisplayed(),
                "Success modal should be displayed after adding to cart");
        System.out.println("✓ Success modal displayed");

        System.out.println("Step 6: Click Shopping Cart button in modal...");
        cartPage.clickShoppingCart();
        System.out.println("✓ Navigated to shopping cart");

        System.out.println("Step 7: Verify cart displays added product with correct details...");
        cartPage.verifyCartContents();

        System.out.println("\n========== TC-CART-001 PASSED ✓ ==========");
    }
}