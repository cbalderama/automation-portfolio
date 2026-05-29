package com.qa.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        waitForVisibility(emailInput);
        type(emailInput, email);
        logInfo("Entered email: " + email);
    }

    public void enterPassword(String password) {
        waitForVisibility(passwordInput);
        type(passwordInput, password);
        logInfo("Entered password: ********");
    }

    public void clickLogin() {
        waitForClickable(loginButton);
        click(loginButton);
        logInfo("Clicked Login button");
    }

    public void login(String email, String password) {
        logStep("Logging in as: " + email);
        enterEmail(email);
        enterPassword(password);
        clickLogin();

        // Wait for catalog screen to confirm login succeeded
        waitForVisibility(catalogScreen);
        logStep("✓ Login successful — catalog screen loaded");

        // 📸 Checkpoint 1 — After login
        takeStepScreenshot("01_After_Login");
    }
}