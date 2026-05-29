package com.qa.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        System.out.println("Verifying product detail page...");

        assertTrue(isElementDisplayed(productTitle), "Product title should be displayed");
        System.out.println("✓ Product Title: " + getJsText(productTitle));

        assertTrue(isElementDisplayed(productPrice), "Product price should be displayed");
        System.out.println("✓ Product Price: " + getJsText(productPrice));

        assertTrue(isElementDisplayed(productRating), "Product rating should be displayed");
        System.out.println("✓ Product Rating: " + getJsText(productRating));

        assertTrue(isElementDisplayed(descriptionSection), "Description section should be displayed");
        System.out.println("✓ Description section visible");

        assertTrue(isElementDisplayed(detailsSection), "Details section should be displayed");
        System.out.println("✓ Details section visible");

        System.out.println("✓ Full product information confirmed");
    }

    public void verifyDefaultQuantity() {
        waitForVisibility(quantityDisplay);
        String quantity = getJsText(quantityDisplay);
        assertTrue(quantity.equals("1"), "Default quantity should be 1, but got: " + quantity);
        System.out.println("✓ Default quantity confirmed: 1");
    }

    public void incrementQuantity() {
        waitForVisibility(quantityDisplay);
        String beforeQty = getJsText(quantityDisplay);
        System.out.println("DEBUG: Quantity before click = '" + beforeQty + "'");

        waitForClickable(increaseQtyBtn);
        jsClick(increaseQtyBtn);
        System.out.println("DEBUG: Clicked plus button via JS");

        // Wait for quantity to update to 2
        wait.until(ExpectedConditions.attributeContains(
                By.xpath("//*[@data-testid='quantity-display']"),
                "textContent", "2"
        ));

        String afterQty = getJsText(quantityDisplay);
        System.out.println("DEBUG: Quantity after click = '" + afterQty + "'");
    }

    public void verifyQuantityUpdated() {
        String quantity = getJsText(quantityDisplay);
        System.out.println("DEBUG: Quantity text = '" + quantity + "'");
        assertTrue(quantity.equals("2"), "Quantity should be 2, but got: " + quantity);
        System.out.println("✓ Quantity verified: 2");
    }

    public void clickAddToCart() {
        waitForClickable(addToCartButton);
        jsClick(addToCartButton);
        System.out.println("✓ Clicked Add to Cart");
    }
}