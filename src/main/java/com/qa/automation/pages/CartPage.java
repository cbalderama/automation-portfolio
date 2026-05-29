package com.qa.automation.pages;

import com.qa.automation.utils.ConfigReader;
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
            return isElementDisplayed(successModalTitle)
                    && isElementDisplayed(successModalMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickShoppingCart() {
        waitForClickable(modalGoToCart);
        click(modalGoToCart);
        System.out.println("✓ Clicked Navigate to Shopping Cart button");
    }

    public void clickContinueShopping() {
        waitForClickable(modalContinueShopping);
        click(modalContinueShopping);
        System.out.println("✓ Clicked Continue Shopping button");
    }

    public void verifyCartLoaded() {
        waitForVisibility(cartScreen);
        assertTrue(isElementDisplayed(cartScreen), "Cart screen should be displayed");
        System.out.println("✓ Cart screen loaded");
    }

    public void verifyCartIsEmpty() {
        waitForVisibility(cartEmpty);
        assertTrue(isElementDisplayed(cartEmpty), "Cart should be empty");
        String emptyTitle = getJsText(cartEmptyTitle);
        assertEquals("Your cart is empty", emptyTitle,
                "Empty cart title should be 'Your cart is empty' but got: '" + emptyTitle + "'");
        System.out.println("✓ Cart is empty — verified");
    }

    public void verifyCartContents() {
        System.out.println("Verifying cart contents...");

        // Wait for cart screen
        waitForPresence(By.xpath("//*[@data-testid='cart-screen']"));

        // Product Name
        WebElement cartProductName = waitForPresence(
                By.xpath("//*[@data-testid='cart-item-lenovo-thinkpad-x1-carbon-name']")
        );
        String actualProductName = getJsText(cartProductName);
        assertEquals(ConfigReader.getProductName(), actualProductName,
                "Product name should be '" + ConfigReader.getProductName() + "' but got: '" + actualProductName + "'");
        System.out.println("✓ Product Name: " + actualProductName);

        // Quantity
        WebElement cartQuantity = waitForPresence(
                By.xpath("//*[@data-testid='cart-item-lenovo-thinkpad-x1-carbon-qty']")
        );
        String actualQuantity = getJsText(cartQuantity);
        assertEquals(ConfigReader.getProductQuantity(), actualQuantity,
                "Quantity should be '" + ConfigReader.getProductQuantity() + "' but got: '" + actualQuantity + "'");
        System.out.println("✓ Quantity: " + actualQuantity);

        // Unit Price
        WebElement cartUnitPrice = waitForPresence(
                By.xpath("//*[@data-testid='cart-item-lenovo-thinkpad-x1-carbon-price']")
        );
        String actualUnitPrice = getJsText(cartUnitPrice);
        assertEquals(ConfigReader.getProductPrice(), actualUnitPrice,
                "Unit price should be '" + ConfigReader.getProductPrice() + "' but got: '" + actualUnitPrice + "'");
        System.out.println("✓ Unit Price: " + actualUnitPrice);

        // Subtotal
        WebElement cartSubtotal = waitForPresence(
                By.xpath("//*[@data-testid='cart-item-lenovo-thinkpad-x1-carbon-subtotal']")
        );
        String actualSubtotal = getJsText(cartSubtotal);
        assertEquals(ConfigReader.getProductSubtotal(), actualSubtotal,
                "Subtotal should be '" + ConfigReader.getProductSubtotal() + "' but got: '" + actualSubtotal + "'");
        System.out.println("✓ Subtotal: " + actualSubtotal);

        // Cart Total
        WebElement cartTotalElement = waitForPresence(
                By.xpath("//*[@data-testid='cart-total']")
        );
        String actualTotal = getJsText(cartTotalElement);
        assertEquals(ConfigReader.getCartTotal(), actualTotal,
                "Cart total should be '" + ConfigReader.getCartTotal() + "' but got: '" + actualTotal + "'");
        System.out.println("✓ Cart Total: " + actualTotal);

        System.out.println("✓ Cart contents verified");
    }

    public void clickCheckout() {
        waitForClickable(checkoutButton);
        click(checkoutButton);
        System.out.println("✓ Clicked Proceed to Checkout");
    }
}