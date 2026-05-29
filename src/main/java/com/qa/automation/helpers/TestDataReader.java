package com.qa.automation.helpers;

import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestDataReader {

    private static final String TEST_DATA_PATH = "/testdata/cart_test_data.json";
    private static JSONObject data;

    // ─── Load JSON from classpath ─────────────────────────────────────────────────

    static {
        try {
            InputStream is = TestDataReader.class.getResourceAsStream(TEST_DATA_PATH);
            if (is == null) {
                throw new RuntimeException("Test data file not found in classpath: " + TEST_DATA_PATH);
            }
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            data = new JSONObject(content);
            System.out.println("✓ Test data loaded from classpath: " + TEST_DATA_PATH);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data: " + e.getMessage(), e);
        }
    }

    // ─── Product Getters ─────────────────────────────────────────────────────────

    public static String getProductName() {
        return data.getJSONObject("product").getString("name");
    }

    public static String getProductPrice() {
        return data.getJSONObject("product").getString("price");
    }

    public static String getProductQuantity() {
        return data.getJSONObject("product").getString("quantity");
    }

    public static String getProductSubtotal() {
        return data.getJSONObject("product").getString("subtotal");
    }

    // ─── Cart Getters ─────────────────────────────────────────────────────────────

    public static String getCartTotal() {
        return data.getJSONObject("cart").getString("total");
    }
}