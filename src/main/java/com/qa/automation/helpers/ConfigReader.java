package com.qa.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = "src/main/resources/config.properties";

    // ─── Load Properties ─────────────────────────────────────────────────────────

    static {
        try {
            FileInputStream file = new FileInputStream(CONFIG_PATH);
            properties = new Properties();
            properties.load(file);
            file.close();
            System.out.println("✓ Config loaded from: " + CONFIG_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties from: " + CONFIG_PATH, e);
        }
    }

    // ─── Getters ─────────────────────────────────────────────────────────────────

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    public static String getAppUrl() {
        return get("app.url");
    }

    public static String getTestEmail() {
        return get("test.email");
    }

    public static String getTestPassword() {
        return get("test.password");
    }

    public static String getBrowser() {
        return get("browser");
    }

    public static String getProductName() {
        return get("product.name");
    }

    public static String getProductPrice() {
        return get("product.price");
    }

    public static String getProductQuantity() {
        return get("product.quantity");
    }

    public static String getProductSubtotal() {
        return get("product.subtotal");
    }

    public static String getCartTotal() {
        return get("cart.total");
    }
}