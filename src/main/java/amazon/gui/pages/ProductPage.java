package amazon.gui.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import io.qameta.allure.Step;
import amazon.utils.elements.ElementActions;
import amazon.utils.elements.ElementWaits;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    private WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By freeShippingFilter = By.xpath("//span[contains(text(), 'Free Shipping')]//ancestor::a");
    private final By newConditionFilter = By.xpath("//span[text()='New']/parent::a");
    private final By sortDropdown = By.cssSelector("select[aria-label='Sort by:']");
    private final By productItems = By.cssSelector("div[data-component-type='s-search-result']");
    private final By productPrice = By.cssSelector("span.a-price-whole");
    private final By addToCartButton = By.xpath("//button[text()='Add to cart']");
    private final By nextPageButton = By.cssSelector("a.s-pagination-next");
    private final By cartIcon = By.cssSelector("a#nav-cart");

    // Actions
    @Step("Apply filters")
    public ProductPage applyFilters() {
        // Apply Free Shipping filter if available
        ElementWaits.waitForVisibility(freeShippingFilter, driver);
        ElementActions.click(freeShippingFilter, driver);
        ElementActions.waitForPageLoad(driver);

        // Apply New condition filter if available
        ElementWaits.waitForVisibility(newConditionFilter, driver);
        ElementActions.click(newConditionFilter, driver);
        ElementActions.waitForPageLoad(driver);
        return this;
    }

    @Step("Sort by price high to low")
    public ProductPage sortByPriceHighToLow() throws InterruptedException {
        ElementWaits.waitForVisibility(sortDropdown, driver);
        ElementActions.selectByText(sortDropdown, driver, "Price: High to Low");
        ElementActions.waitForPageLoad(driver);
        return this;
    }

    @Step("Add products below price threshold")
    public ProductPage addProductsBelowPrice(float priceThreshold) {
        try {
            boolean hasNextPage = true;
            while (hasNextPage) {
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productItems));
                List<WebElement> products = driver.findElements(productItems);

                for (WebElement product : products) {
                    try {
                        String priceText = product.findElement(productPrice).getText().replace(",", "");
                        int price = Integer.parseInt(priceText);

                        if (price < priceThreshold) {
                            WebElement addToCartBtn = product.findElement(addToCartButton);
                            addToCartBtn.click();
                        }
                    } catch (Exception e) {
                        // Handle cases where price or button is not found
                        System.out.println("Skipping a product due to missing elements.");
                    }
                }

                // Check if there is a next page and navigate
                List<WebElement> nextPageElements = driver.findElements(nextPageButton);
                if (!nextPageElements.isEmpty()) {
                    nextPageElements.get(0).click();
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productItems)); // Wait for next page to load
                } else {
                    hasNextPage = false; // No more pages
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return this;
    }


    @Step("Go to cart")
    public CartPage goToCart() {
        ElementActions.click(cartIcon, driver);
        return new CartPage(driver);
    }
}
