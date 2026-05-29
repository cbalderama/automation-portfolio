package com.qa.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductCatalogPage extends BasePage {

    // ─── Locators — all using data-testid ────────────────────────────────────────

    @FindBy(xpath = "//*[@data-testid='product-card']")
    private List<WebElement> productCards;

    @FindBy(xpath = "//*[@data-testid='product-card-lenovo-thinkpad-x1-carbon']")
    private WebElement lenovoProductCard;

    // ─── Constructor ─────────────────────────────────────────────────────────────

    public ProductCatalogPage(WebDriver driver) {
        super(driver);
    }

    // ─── Actions ─────────────────────────────────────────────────────────────────

    public void verifyCatalogLoaded() {
        System.out.println("Waiting for catalog to load...");

        // Wait for at least one product card to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@data-testid='product-card']")
        ));

        assertTrue(productCards.size() > 0, "Product cards should be displayed");
        System.out.println("✓ Found " + productCards.size() + " products");

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Verify each card has name and price
        for (int i = 0; i < productCards.size(); i++) {
            WebElement card = productCards.get(i);

            // Check for image
            List<WebElement> images = card.findElements(By.tagName("img"));
            assertTrue(images.size() > 0, "Product " + (i + 1) + " should have an image");

            // Check for product name
            List<WebElement> names = card.findElements(
                    By.xpath(".//*[@data-testid='catalog-product-name']")
            );
            assertTrue(names.size() > 0, "Product " + (i + 1) + " should have a name");

            // Check for price
            List<WebElement> prices = card.findElements(
                    By.xpath(".//*[@data-testid='catalog-product-price']")
            );
            assertTrue(prices.size() > 0, "Product " + (i + 1) + " should have a price");

            String productName = ((String) js.executeScript(
                    "return arguments[0].textContent;", names.get(0))).trim();
            String productPrice = ((String) js.executeScript(
                    "return arguments[0].textContent;", prices.get(0))).trim();
            System.out.println("✓ Product " + (i + 1) + ": " + productName + " - " + productPrice);
        }
    }

    public void clickProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(lenovoProductCard));
        click(lenovoProductCard);
        System.out.println("✓ Clicked Lenovo ThinkPad X1 Carbon");
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────────

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}