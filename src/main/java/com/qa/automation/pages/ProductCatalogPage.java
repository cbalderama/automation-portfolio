package com.qa.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class ProductCatalogPage extends BasePage {

    @FindBy(xpath = "//div[@class='css-view-g5y9jx r-transitionProperty-1i6wzkk r-userSelect-lrvibr r-cursor-1loqt21 r-touchAction-1otgn73 r-backgroundColor-14lw9ot r-borderRadius-1q9bdsx r-boxShadow-1a0psd9 r-marginBottom-1ifxtd0 r-overflow-1udh08x']")
    private List<WebElement> productCards;

    @FindBy(xpath = "//div[contains(@class, 'r-WebkitBoxOrient-8akbws') and contains(text(), 'Lenovo ThinkPad X1 Carbon')]/..")
    private WebElement lenovoProductCard;

    public ProductCatalogPage(WebDriver driver) {
        super(driver);
    }

    public void verifyCatalogLoaded() {
        // Wait for products to load
        try {
            Thread.sleep(3000); // Wait 3 seconds for products to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(productCards.size() > 0, "Product cards should be displayed");
        System.out.println("✓ Found " + productCards.size() + " products");

        // Verify each card has image, name, and price
        for (int i = 0; i < productCards.size(); i++) {
            WebElement card = productCards.get(i);

            // Check for image
            List<WebElement> images = card.findElements(org.openqa.selenium.By.tagName("img"));
            assertTrue(images.size() > 0, "Product " + (i+1) + " should have an image");

            // Check for product name
            List<WebElement> names = card.findElements(org.openqa.selenium.By.xpath(".//div[contains(@class, 'r-WebkitBoxOrient-8akbws')]"));
            assertTrue(names.size() > 0, "Product " + (i+1) + " should have a name");

            // Check for price (contains $)
            List<WebElement> prices = card.findElements(org.openqa.selenium.By.xpath(".//div[contains(@class, 'r-color-126xxis')]"));
            assertTrue(prices.size() > 0, "Product " + (i+1) + " should have a price");

            String productName = names.get(0).getText();
            String productPrice = prices.get(0).getText();
            System.out.println("✓ Product " + (i+1) + ": " + productName + " - " + productPrice);
        }
    }

    public void clickProduct() {
        click(lenovoProductCard);
    }

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}