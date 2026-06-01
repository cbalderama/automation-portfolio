
#E-Commerce Cart Automation Framework

> A production-grade Selenium automation framework built for a React Native Expo e-commerce application — featuring Page Object Model, data-testid selectors, Extent Reports with screenshots, and JSON-driven test data.

---

## Key Highlights

- **Page Object Model** — clean separation of test logic and page interactions
- **data-testid selectors** — stable, purpose-built locators that survive UI changes
- **Extent Reports** — per-step HTML reports with 7 embedded screenshots
- **JSON Test Data** — product data externalized, zero hardcoded values in tests
- **ConfigReader** — environment config separate from code
- **Custom Utilities** — WaitUtils, JavaScriptUtils, AssertionHelper, ScreenshotUtils
- **React Native Web** — overcame JS event handling challenges with JavascriptExecutor
- **Login Verification** — explicit wait confirms authentication before proceeding

---

## Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 17 | Core language |
| Selenium WebDriver | 4.15.0 | Browser automation |
| JUnit Jupiter | 5.11.0 | Test framework |
| WebDriverManager | 5.6.3 | Automatic driver management |
| Extent Reports | 5.1.1 | HTML test reporting |
| Maven | 3.x | Build and dependency management |
| Chrome | 148 | Test browser |

---

## Architecture

```
src/
├── main/java/com/qa/automation/
│   ├── pages/                    # Page Object Model
│   │   ├── BasePage.java         # Core interactions, waits, logging, screenshots
│   │   ├── LoginPage.java        # Login flow
│   │   ├── ProductCatalogPage.java  # Product listing
│   │   ├── ProductDetailPage.java   # Product detail + quantity
│   │   └── CartPage.java         # Cart verification
│   │
│   ├── utils/                    # Technical infrastructure
│   │   ├── DriverManager.java    # WebDriver lifecycle
│   │   ├── ConfigReader.java     # config.properties reader
│   │   ├── WaitUtils.java        # Explicit wait strategies
│   │   └── JavaScriptUtils.java  # JS execution helpers
│   │
│   ├── helpers/                  # Test-level reusable logic
│   │   ├── AssertionHelper.java  # Custom assertions
│   │   ├── ScreenshotUtils.java  # Screenshot capture
│   │   └── TestDataReader.java   # JSON test data reader
│   │
│   └── reports/                  # Reporting infrastructure
│       └── ExtentReportManager.java  # Extent Reports setup
│
├── test/java/com/qa/automation/
│   ├── tests/
│   │   └── CartHappyPathTest.java    # TC-CART-001
│   └── listeners/
│       └── TestListener.java         # JUnit extension for pass/fail hooks
│
├── src/main/resources/
│   └── config.properties             # App URL, credentials, browser
│
└── src/test/resources/
└── testdata/
└── cart_test_data.json       # Product test data
```

---

## Test Coverage

### TC-CART-001: Add Product to Cart (Happy Path)

| Step | Action | Verification |
|---|---|---|
| 1 | Login as test user | Catalog screen loads |
| 2 | Verify product catalog | 16 products with image, name, price |
| 3 | Navigate to product detail | Title, price, rating, description, details |
| 4 | Set quantity to 2 | Quantity display updates to 2 |
| 5 | Click Add to Cart | Success modal appears |
| 6 | Navigate to cart | Cart screen loads |
| 7 | Verify cart contents | Product name, quantity, price, subtotal, total |

---

## Test Report

The framework generates a full HTML report with per-step logging and screenshots at key checkpoints:

| # | Checkpoint | Screenshot |
|---|---|---|
| 1 | After login 
| 2 | Catalog loaded
| 3 | Product detail page
| 4 | Quantity updated
| 5 | Success modal
| 6 | Cart contents verified
| 7 | Final state

> Open `test-output/extent-report.html` after running to view the full report.

---

## Configuration

### `config.properties`
```properties
# Application
app.url=http://localhost:8081

# Test Credentials
test.email=test@email.com
test.password=password

# Browser
browser=chrome
```

### `cart_test_data.json`
```json
{
  "product": {
    "name": "Lenovo ThinkPad X1 Carbon",
    "price": "$1599.00",
    "quantity": "2",
    "subtotal": "$3198.00"
  },
  "cart": {
    "total": "$3198.00"
  }
}
```

---

## How to Run

### Prerequisites
- Java 17+
- Maven 3.x
- Chrome browser
- React Native app running at `http://localhost:8081`

### Run all tests
```bash
mvn clean test
```

### Run specific test
```bash
mvn clean test -Dtest=CartHappyPathTest
```

### View report
```bash
# After test run, open in browser:
test-output/extent-report.html
```

---

## Technical Challenges Solved

### 1. React Native Web — JS Event Handling
React Native Web uses synthetic events that Selenium's standard `.click()` cannot trigger reliably. Solved by using `JavascriptExecutor`:

```java
js.executeScript("arguments[0].click();", element);
```

### 2. Dynamic Class Selectors → data-testid
React Native Web generates dynamic CSS class names (e.g. `r-color-1khnkhu`) that change on rebuild. Solved by adding `testID` props to all interactive elements:

```tsx
// React Native component
<TouchableOpacity testID="add-to-cart-btn">

// Selenium selector
By.xpath("//*[@data-testid='add-to-cart-btn']")
```

### 3. webkit-line-clamp Text Extraction
React Native Web's text truncation via `-webkit-line-clamp` causes `getText()` to return empty string. Solved using JavaScript `textContent`:

```java
js.executeScript("return arguments[0].textContent;", element)
```

### 4. Virtualized List Rendering
React Native's `FlatList` uses virtualization — elements exist in DOM but are not "visible". Solved using `presenceOfElementLocated` instead of `visibilityOfElementLocated`.

## Author

**Cydrick Balderama**
QA Engineer | 4+ years experience
- Manual Testing & Automation
- React Native Expo App (Developer + QA)
- E-commerce domain expertise

---

## Framework Metrics

| Metric | Value |
|---|---|
| Test Cases | 1 (TC-CART-001) |
| Assertions per test | 9 |
| Screenshots per run | 7 |
| Page Objects | 5 |
| Utility Classes | 6 |
| Selector Strategy | data-testid (100%) |