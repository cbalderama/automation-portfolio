package com.qa.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    // ─── Modal Elements ───────────────────────────────────────────────────────────

    @FindBy(xpath = "//div[contains(@class, 'r-color-1khnkhu') and contains(@class, 'r-fontSize-evnaw') and contains(text(), 'Success!')]")
    private WebElement successModalTitle;

    @FindBy(xpath = "//div[contains(@class, 'r-color-djgu52') and contains(text(), 'Added to cart!')]")
    private WebElement successModalMessage;

    @FindBy(xpath = "//div[contains(@class, 'r-color-jwli3a') and contains(text(), 'Navigate to Shopping Cart')]")
    private WebElement shoppingCartButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ─── Actions ─────────────────────────────────────────────────────────────────

    public boolean isSuccessModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successModalTitle));
            return isElementDisplayed(successModalTitle)
                    && isElementDisplayed(successModalMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickShoppingCart() {
        wait.until(ExpectedConditions.elementToBeClickable(shoppingCartButton));
        click(shoppingCartButton);
        System.out.println("✓ Clicked Navigate to Shopping Cart button");
    }

    public void verifyCartContents() {
        System.out.println("Verifying cart contents...");

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        // Product Name
        WebElement cartProductName = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'r-textOverflow-1udbk01') and contains(text(), 'Lenovo ThinkPad X1 Carbon')]")
        ));
        String actualProductName = cartProductName.getText();
        assertEquals("Lenovo ThinkPad X1 Carbon", actualProductName,
                "Product name should be 'Lenovo ThinkPad X1 Carbon' but got: '" + actualProductName + "'");
        System.out.println("✓ Product Name: " + actualProductName);

        // Quantity
        WebElement cartQuantity = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'r-minWidth-lp5zef') and contains(@class, 'r-textAlign-q4m81j')]")
        ));
        String actualQuantity = cartQuantity.getText();
        assertEquals("2", actualQuantity,
                "Quantity should be '2' but got: '" + actualQuantity + "'");
        System.out.println("✓ Quantity: " + actualQuantity);

        // Unit Price
        WebElement cartUnitPrice = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'r-color-126xxis') and contains(@class, 'r-fontSize-1b43r93') and contains(@class, 'r-fontWeight-1kfrs79')]")
        ));
        String actualUnitPrice = cartUnitPrice.getText();
        assertEquals("$1599.00", actualUnitPrice,
                "Unit price should be '$1599.00' but got: '" + actualUnitPrice + "'");
        System.out.println("✓ Unit Price: " + actualUnitPrice);

        // Subtotal
        WebElement cartSubtotal = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'r-fontSize-1i10wst') and contains(@class, 'r-fontWeight-b88u0q')]")
        ));
        String actualSubtotal = cartSubtotal.getText();
        assertEquals("$3198.00", actualSubtotal,
                "Subtotal should be '$3198.00' but got: '" + actualSubtotal + "'");
        System.out.println("✓ Subtotal: " + actualSubtotal);

        System.out.println("✓ Cart contents verified");
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────────

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private void assertEquals(String expected, String actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message);
        }
    }
}