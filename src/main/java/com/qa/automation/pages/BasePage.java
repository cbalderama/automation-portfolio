package com.qa.automation.pages;

import com.qa.automation.helpers.AssertionHelper;
import com.qa.automation.utils.JavaScriptUtils;
import com.qa.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WaitUtils waitUtils;
    protected JavaScriptUtils jsUtils;

    // ─── Constructor ─────────────────────────────────────────────────────────────

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.waitUtils = new WaitUtils(driver);
        this.jsUtils = new JavaScriptUtils(driver);
        PageFactory.initElements(driver, this);
    }

    // ─── Core Interactions ────────────────────────────────────────────────────────

    protected void click(WebElement element) {
        waitUtils.forClickable(element);
        element.click();
    }

    protected void jsClick(WebElement element) {
        waitUtils.forClickable(element);
        jsUtils.click(element);
    }

    protected void type(WebElement element, String text) {
        waitUtils.forVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    // ─── Text Retrieval ───────────────────────────────────────────────────────────

    protected String getText(WebElement element) {
        return element.getText();
    }

    protected String getJsText(WebElement element) {
        return jsUtils.getText(element);
    }

    // ─── Visibility Checks ────────────────────────────────────────────────────────

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ─── Wait Helpers ─────────────────────────────────────────────────────────────

    protected WebElement waitForVisibility(WebElement element) {
        return waitUtils.forVisibility(element);
    }

    protected WebElement waitForPresence(By locator) {
        return waitUtils.forPresence(locator);
    }

    protected WebElement waitForClickable(WebElement element) {
        return waitUtils.forClickable(element);
    }

    protected void waitForTextPresent(By locator, String text) {
        waitUtils.forTextPresent(locator, text);
    }

    protected void waitForUrlContains(String urlFragment) {
        waitUtils.forUrlContains(urlFragment);
    }

    protected void waitForAttributeContains(By locator, String attribute, String value) {
        waitUtils.forAttributeContains(locator, attribute, value);
    }

    // ─── Assertion Helpers ────────────────────────────────────────────────────────

    protected void assertTrue(boolean condition, String message) {
        AssertionHelper.assertTrue(condition, message);
    }

    protected void assertEquals(String expected, String actual, String message) {
        AssertionHelper.assertEquals(expected, actual, message);
    }

    protected void assertNotEmpty(String text, String message) {
        AssertionHelper.assertNotEmpty(text, message);
    }

    protected void assertContains(String text, String substring, String message) {
        AssertionHelper.assertContains(text, substring, message);
    }
}