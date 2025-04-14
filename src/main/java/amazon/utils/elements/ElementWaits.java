package amazon.utils.elements;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementWaits {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    private static final Duration POLLING_INTERVAL = Duration.ofMillis(500);

    /**
     * Waits for element to be present in the DOM
     */
    public static WebElement waitForPresence(final By locator, final WebDriver driver) {
        return waitWithTimeout(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }


    /**
     * Waits for element to be visible
     */
    public static WebElement waitForVisibility(final By locator, final WebDriver driver) {
        return waitWithTimeout(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for element to be clickable
     */
    public static WebElement waitForClickable(final By locator, final WebDriver driver) {
        return waitWithTimeout(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for element to become invisible
     */
    public static boolean waitForInvisibility(final By locator, final WebDriver driver) {
        return waitWithTimeout(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits for a custom condition with fluent wait
     */
    public static WebElement waitForCustomCondition(final WebDriver driver, final By locator, 
            final String errorMessage, final Duration timeout) {
        return new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(POLLING_INTERVAL)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(errorMessage)
                .until(d -> {
                    WebElement element = d.findElement(locator);
                    return element.isDisplayed() ? element : null;
                });
    }

    /**
     * Creates a WebDriverWait instance with specified timeout
     */
    private static WebDriverWait waitWithTimeout(final WebDriver driver, final Duration timeout) {
        return new WebDriverWait(driver, timeout);
    }
}