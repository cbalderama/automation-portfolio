package com.qa.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        logStep("Waiting for catalog to load...");

        // Wait for at least one product card to appear
        waitForPresence(By.xpath("//*[@data-testid='product-card']"));

        assertTrue(productCards.size() > 0, "Product cards should be displayed");
        logStep("✓ Found " + productCards.size() + " products");

        // Verify each card has image, name, and price
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

            String productName = jsUtils.getText(names.get(0));
            String productPrice = jsUtils.getText(prices.get(0));
            logInfo("✓ Product " + (i + 1) + ": " + productName + " - " + productPrice);
        }

        logStep("✓ Catalog verified — all " + productCards.size() + " products loaded");
    }

    public void clickProduct() {
        waitForClickable(lenovoProductCard);
        click(lenovoProductCard);
        logStep("✓ Clicked Lenovo ThinkPad X1 Carbon");
    }
}