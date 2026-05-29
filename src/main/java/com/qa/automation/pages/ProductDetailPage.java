package com.qa.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailPage extends BasePage {

    // ─── Locators — all using data-testid ────────────────────────────────────────

    @FindBy(xpath = "//*[@data-testid='product-name']")
    private WebElement productTitle;

    @FindBy(xpath = "//*[@data-testid='product-price']")
    private WebElement productPrice;

    @FindBy(xpath = "//*[@data-testid='product-rating']")
    private WebElement productRating;

    @FindBy(xpath = "//*[@data-testid='description-section']")
    private WebElement descriptionSection;

    @FindBy(xpath = "//*[@data-testid='details-section']")
    private WebElement detailsSection;

    @FindBy(xpath = "//*[@data-testid='quantity-display']")
    private WebElement quantityDisplay;

    @FindBy(xpath = "//*[@data-testid='increase-qty-btn']")
    private WebElement increaseQtyBtn;

    @FindBy(xpath = "//*[@data-testid='add-to-cart-btn']")
    private WebElement addToCartButton;

    // ─── Constructor ─────────────────────────────────────────────────────────────

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    // ─── Actions ─────────────────────────────────────────────────────────────────

    public void verifyProductDetails() {
        logStep("Verifying product detail page...");

        assertTrue(isElementDisplayed(productTitle), "Product title should be displayed");
        logStep("✓ Product Title: " + getJsText(productTitle));

        assertTrue(isElementDisplayed(productPrice), "Product price should be displayed");
        logStep("✓ Product Price: " + getJsText(productPrice));

        assertTrue(isElementDisplayed(productRating), "Product rating should be displayed");
        logStep("✓ Product Rating: " + getJsText(productRating));

        assertTrue(isElementDisplayed(descriptionSection), "Description section should be displayed");
        logStep("✓ Description section visible");

        assertTrue(isElementDisplayed(detailsSection), "Details section should be displayed");
        logStep("✓ Details section visible");

        logStep("✓ Full product information confirmed");

        // 📸 Checkpoint 2 — Product detail page loaded
        takeStepScreenshot("02_Product_Detail_Page");
    }

    public void verifyDefaultQuantity() {
        waitForVisibility(quantityDisplay);
        String quantity = getJsText(quantityDisplay);
        assertTrue(quantity.equals("1"), "Default quantity should be 1, but got: " + quantity);
        logStep("✓ Default quantity confirmed: 1");
    }

    public void incrementQuantity() {
        waitForVisibility(quantityDisplay);
        String beforeQty = getJsText(quantityDisplay);
        logInfo("Quantity before click = '" + beforeQty + "'");

        waitForClickable(increaseQtyBtn);
        jsClick(increaseQtyBtn);
        logInfo("Clicked plus button via JS");

        // Wait for quantity to update to 2
        waitForAttributeContains(
                By.xpath("//*[@data-testid='quantity-display']"),
                "textContent", "2"
        );

        String afterQty = getJsText(quantityDisplay);
        logStep("✓ Quantity updated to: " + afterQty);

        // 📸 Checkpoint 3 — After quantity update
        takeStepScreenshot("03_Quantity_Updated");
    }

    public void verifyQuantityUpdated() {
        String quantity = getJsText(quantityDisplay);
        assertTrue(quantity.equals("2"), "Quantity should be 2, but got: " + quantity);
        logStep("✓ Quantity verified: " + quantity);
    }

    public void clickAddToCart() {
        waitForClickable(addToCartButton);
        jsClick(addToCartButton);
        logStep("✓ Clicked Add to Cart");
    }
}