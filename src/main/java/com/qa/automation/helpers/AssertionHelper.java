package com.qa.automation.helpers;

public class AssertionHelper {

    // ─── Equality Assertions ─────────────────────────────────────────────────────

    public static void assertEquals(String expected, String actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(
                    message + "\n  Expected: '" + expected + "'\n  Actual:   '" + actual + "'"
            );
        }
    }

    public static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(
                    message + "\n  Expected: '" + expected + "'\n  Actual:   '" + actual + "'"
            );
        }
    }

    // ─── Boolean Assertions ──────────────────────────────────────────────────────

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    // ─── Null Assertions ─────────────────────────────────────────────────────────

    public static void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new AssertionError(message);
        }
    }

    public static void assertNull(Object object, String message) {
        if (object != null) {
            throw new AssertionError(message + " — expected null but got: '" + object + "'");
        }
    }

    // ─── String Assertions ───────────────────────────────────────────────────────

    public static void assertContains(String text, String substring, String message) {
        if (!text.contains(substring)) {
            throw new AssertionError(
                    message + "\n  Text:      '" + text + "'\n  Expected to contain: '" + substring + "'"
            );
        }
    }

    public static void assertNotEmpty(String text, String message) {
        if (text == null || text.trim().isEmpty()) {
            throw new AssertionError(message + " — text was null or empty");
        }
    }
}