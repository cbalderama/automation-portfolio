package com.qa.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductDetailPage extends BasePage {

    @FindBy(xpath = "//div[contains(@class, 'r-color-1khnkhu r-fontSize-1x35g6') and contains(text(), 'Lenovo ThinkPad X1 Carbon')]")
    private WebElement productTitle;

    @FindBy(xpath = "//div[contains(@class, 'r-color-126xxis r-fontSize-1ui5ee8') and contains(text(), '$')]")
    private WebElement productPrice;

    @FindBy(xpath = "//div[contains(@class, 'r-color-1khnkhu r-fontSize-ubezar r-fontWeight-1kfrs79 r-marginRight-1d4mawv')]")
    private WebElement productRating;

    @FindBy(xpath = "//div[contains(text(), 'Description')]")
    private WebElement descriptionSection;

    @FindBy(xpath = "//div[contains(text(), 'Details')]")
    private WebElement detailsSection;

    @FindBy(xpath = "//div[contains(@class, 'r-color-1khnkhu r-fontSize-1i10wst r-fontWeight-1kfrs79 r-marginInline-1xpp3t0')]")
    private WebElement quantityDisplay;

    @FindBy(xpath = "//div[contains(text(), 'Add to Cart')]")
    private WebElement addToCartButton;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    // ─── Actions ─────────────────────────────────────────────────────────────────

    public void verifyProductDetails() {
        System.out.println("Verifying product detail page...");

        assertTrue(isElementDisplayed(productTitle), "Product title should be displayed");
        System.out.println("✓ Product Title: " + getText(productTitle));

        assertTrue(isElementDisplayed(productPrice), "Product price should be displayed");
        System.out.println("✓ Product Price: " + getText(productPrice));

        assertTrue(isElementDisplayed(productRating), "Product rating should be displayed");
        System.out.println("✓ Product Rating: " + getText(productRating));

        assertTrue(isElementDisplayed(descriptionSection), "Description section should be displayed");
        System.out.println("✓ Description section visible");

        assertTrue(isElementDisplayed(detailsSection), "Details section should be displayed");
        System.out.println("✓ Details section visible");

        System.out.println("✓ Full product information confirmed");
    }

    public void incrementQuantity() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Wait for quantity display to be visible first
        wait.until(ExpectedConditions.visibilityOf(quantityDisplay));
        String beforeQty = getText(quantityDisplay);
        System.out.println("DEBUG: Quantity before click = '" + beforeQty + "'");

        // Find the plus button and click ONCE (1 → 2)
        WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class, 'r-backgroundColor-cb25cm')]//div[@tabindex='0'])[last()]")
        ));

        js.executeScript("arguments[0].click();", plusButton);
        System.out.println("DEBUG: Clicked plus button via JS");

        try { Thread.sleep(800); } catch (InterruptedException e) { e.printStackTrace(); }

        String afterQty = getText(quantityDisplay);
        System.out.println("DEBUG: Quantity after click = '" + afterQty + "'");
    }

    public void verifyQuantityUpdated() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

        String quantity = getText(quantityDisplay);
        System.out.println("DEBUG: Quantity text = '" + quantity + "'");
        assertTrue(quantity.equals("2"), "Quantity should be 2, but got: " + quantity);
        System.out.println("✓ Quantity verified: " + quantity);
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        click(addToCartButton);
        System.out.println("✓ Clicked Add to Cart");
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────────

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}