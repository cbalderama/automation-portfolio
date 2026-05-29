package com.qa.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    // ─── Locators ────────────────────────────────────────────────────────────────

    @FindBy(xpath = "//input[@placeholder='Email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//div[contains(text(), 'Login')]")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@data-testid='catalog-screen']")
    private WebElement catalogScreen;

    // ─── Constructor ─────────────────────────────────────────────────────────────

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ─── Actions ─────────────────────────────────────────────────────────────────

    public void enterEmail(String email) {
        type(emailInput, email);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public void login(String email, String password) {
        System.out.println("Logging in as: " + email);
        enterEmail(email);
        enterPassword(password);
        clickLogin();

        // Wait for catalog screen to confirm login succeeded
        wait.until(ExpectedConditions.visibilityOf(catalogScreen));
        System.out.println("✓ Login successful — catalog screen loaded");
    }
}