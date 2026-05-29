package com.qa.automation.pages;

import com.qa.automation.helpers.TestDataReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    // ─── Locators — all using data-testid ────────────────────────────────────────

    @FindBy(xpath = "//*[@data-testid='modal-title']")
    private WebElement successModalTitle;

    @FindBy(xpath = "//*[@data-testid='modal-message']")
    private WebElement successModalMessage;

    @FindBy(xpath = "//*[@data-testid='modal-go-to-cart']")
    private WebElement modalGoToCart;

    @FindBy(xpath = "//*[@data-testid='modal-continue-shopping']")
    private WebElement modalContinueShopping;

    @FindBy(xpath = "//*[@data-testid='cart-screen']")
    private WebElement cartScreen;

    @FindBy(xpath = "//*[@data-testid='cart-empty']")
    private WebElement cartEmpty;

    @FindBy(xpath = "//*[@data-testid='cart-empty-title']")
    private WebElement cartEmptyTitle;

    @FindBy(xpath = "//*[@data-testid='cart-total']")
    private WebElement cartTotal;

    @FindBy(xpath = "//*[@data-testid='checkout-btn']")
    private WebElement checkoutButton;

    // ─── Constructor ─────────────────────────────────────────────────────────────

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ─── Actions ─────────────────────────────────────────────────────────────────

    public boolean isSuccessModalDisplayed() {
        try {
            waitForVisibility(successModalTitle);

            // 📸 Checkpoint 4 — Success modal appeared
            takeStepScreenshot("05_Success_Modal");

            return isElementDisplayed(successModalTitle)
                    && isElementDisplayed(successModalMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickShoppingCart() {
        waitForClickable(modalGoToCart);
        click(modalGoToCart);
        logStep("✓ Clicked Navigate to Shopping Cart button");
    }

    public void clickContinueShopping() {
        waitForClickable(modalContinueShopping);
        click(modalContinueShopping);
        logStep("✓ Clicked Continue Shopping button");
    }

    public void verifyCartLoaded() {
        waitForVisibility(cartScreen);
        assertTrue(isElementDisplayed(cartScreen), "Cart screen should be displayed");
        logStep("✓ Cart screen loaded");
    }

    public void verifyCartIsEmpty() {
        waitForVisibility(cartEmpty);
        assertTrue(isElementDisplayed(cartEmpty), "Cart should be empty");
        String emptyTitle = getJsText(cartEmptyTitle);
        assertEquals("Your cart is empty", emptyTitle,
                "Empty cart title should be 'Your cart is empty' but got: '" + emptyTitle + "'");
        logStep("✓ Cart is empty — verified");
    }

    public void verifyCartContents() {
        logStep("Verifying cart contents...");

        // Wait for cart screen
        waitForPresence(By.xpath("//*[@data-testid='cart-screen']"));

        // Build dynamic testIDs from product name
        String expectedName = TestDataReader.getProductName();
        String itemTestId = expectedName.toLowerCase().replace(" ", "-");

        // Product Name
        WebElement cartProductName = waitForPresence(
                By.xpath("//*[@data-testid='cart-item-" + itemTestId + "-name']")
        );
        String actualProductName = getJsText(cartProductName);
        assertEquals(expectedName, actualProductName,
                "Product name should be '" + expectedName + "' but got: '" + actualProductName + "'");
        logStep("✓ Product Name: " + actualProductName);

        // Quantity
        String expectedQty = TestDataReader.getProductQuantity();
        WebElement cartQuantity = waitForPresence(
                By.xpath("//*[@data-testid='cart-item-" + itemTestId + "-qty']")
        );
        String actualQuantity = getJsText(cartQuantity);
        assertEquals(expectedQty, actualQuantity,
                "Quantity should be '" + expectedQty + "' but got: '" + actualQuantity + "'");
        logStep("✓ Quantity: " + actualQuantity);

        // Unit Price
        String expectedPrice = TestDataReader.getProductPrice();
        WebElement cartUnitPrice = waitForPresence(
                By.xpath("//*[@data-testid='cart-item-" + itemTestId + "-price']")
        );
        String actualUnitPrice = getJsText(cartUnitPrice);
        assertEquals(expectedPrice, actualUnitPrice,
                "Unit price should be '" + expectedPrice + "' but got: '" + actualUnitPrice + "'");
        logStep("✓ Unit Price: " + actualUnitPrice);

        // Subtotal
        String expectedSubtotal = TestDataReader.getProductSubtotal();
        WebElement cartSubtotal = waitForPresence(
                By.xpath("//*[@data-testid='cart-item-" + itemTestId + "-subtotal']")
        );
        String actualSubtotal = getJsText(cartSubtotal);
        assertEquals(expectedSubtotal, actualSubtotal,
                "Subtotal should be '" + expectedSubtotal + "' but got: '" + actualSubtotal + "'");
        logStep("✓ Subtotal: " + actualSubtotal);

        // Cart Total
        String expectedTotal = TestDataReader.getCartTotal();
        WebElement cartTotalElement = waitForPresence(
                By.xpath("//*[@data-testid='cart-total']")
        );
        String actualTotal = getJsText(cartTotalElement);
        assertEquals(expectedTotal, actualTotal,
                "Cart total should be '" + expectedTotal + "' but got: '" + actualTotal + "'");
        logStep("✓ Cart Total: " + actualTotal);

        logStep("✓ Cart contents verified");

        // 📸 Checkpoint 5 — Cart contents verified
        takeStepScreenshot("06_Cart_Contents_Verified");
    }

    public void clickCheckout() {
        waitForClickable(checkoutButton);
        click(checkoutButton);
        logStep("✓ Clicked Proceed to Checkout");
    }
}