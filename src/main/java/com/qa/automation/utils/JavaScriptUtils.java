package com.qa.automation.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {

    private final JavascriptExecutor js;

    // ─── Constructor ─────────────────────────────────────────────────────────────

    public JavaScriptUtils(WebDriver driver) {
        this.js = (JavascriptExecutor) driver;
    }

    // ─── Click ───────────────────────────────────────────────────────────────────

    public void click(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    // ─── Text Retrieval ──────────────────────────────────────────────────────────

    public String getText(WebElement element) {
        String text = (String) js.executeScript(
                "return arguments[0].textContent;", element);
        return text != null ? text.trim() : "";
    }

    public String getInnerText(WebElement element) {
        String text = (String) js.executeScript(
                "return arguments[0].innerText;", element);
        return text != null ? text.trim() : "";
    }

    public String getValue(WebElement element) {
        String text = (String) js.executeScript(
                "return arguments[0].value;", element);
        return text != null ? text.trim() : "";
    }

    // ─── Scroll ──────────────────────────────────────────────────────────────────

    public void scrollToElement(WebElement element) {
        js.executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
    }

    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // ─── Visibility ──────────────────────────────────────────────────────────────

    public boolean isVisible(WebElement element) {
        return (Boolean) js.executeScript(
                "return arguments[0].offsetParent !== null;", element);
    }

    // ─── Page Info ───────────────────────────────────────────────────────────────

    public String getPageTitle() {
        return (String) js.executeScript("return document.title;");
    }

    public String getCurrentUrl() {
        return (String) js.executeScript("return window.location.href;");
    }

    // ─── Highlight (for debugging) ────────────────────────────────────────────────

    public void highlight(WebElement element) {
        js.executeScript(
                "arguments[0].style.border='3px solid red';", element);
    }

    public void removeHighlight(WebElement element) {
        js.executeScript(
                "arguments[0].style.border='';", element);
    }
}