package amazon.utils.elements;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.Objects;

/**
 * Provides methods for performing actions on elements
 */
public class ElementActions {
    private static final Duration RETRY_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(30);

    /**
     * Types text into an element after waiting for it to be visible and interactable
     */
    public static void type(final By locator, final WebDriver driver, String keys) {
        WebElement element = ElementWaits.waitForVisibility(locator, driver);
        try {
            element.clear();
            element.sendKeys(keys, Keys.ESCAPE);
        } catch (ElementClickInterceptedException e) {
            new WebDriverWait(driver, RETRY_TIMEOUT)
                .until(d -> {
                    try {
                        WebElement el = ElementWaits.waitForVisibility(locator, d);
                        el.clear();
                        el.sendKeys(keys, Keys.ESCAPE);
                        return true;
                    } catch (Exception ex) {
                        return false;
                    }
                });
        }
    }


    /**
     * Types text into an element after waiting for it to be visible and interactable, then clicking escape
     */
    public static void typeAndEnterEsc(final By locator, final WebDriver driver, String keys) {
        WebElement element = ElementWaits.waitForVisibility(locator, driver);
        try {
            element.clear();
            element.sendKeys(keys, Keys.ESCAPE);
        } catch (ElementClickInterceptedException e) {
            new WebDriverWait(driver, RETRY_TIMEOUT)
                    .until(d -> {
                        try {
                            WebElement el = ElementWaits.waitForVisibility(locator, d);
                            el.clear();
                            el.sendKeys(keys, Keys.ESCAPE);
                            return true;
                        } catch (Exception ex) {
                            return false;
                        }
                    });
        }
    }

    /**
     * Clicks an element after waiting for it to be clickable
     */
    public static void click(final By locator, final WebDriver driver) {
        try {
            ElementWaits.waitForClickable(locator, driver).click();
        } catch (ElementClickInterceptedException e) {
            new WebDriverWait(driver, RETRY_TIMEOUT)
                .until(d -> {
                    try {
                        WebElement element = ElementWaits.waitForPresence(locator, d);
                        new Actions(d).moveToElement(element).click().perform();
                        return true;
                    } catch (Exception ex) {
                        try {
                            WebElement element = ElementWaits.waitForPresence(locator, d);
                            ((JavascriptExecutor) d).executeScript("arguments[0].click();", element);
                            return true;
                        } catch (Exception exc) {
                            return false;
                        }
                    }
                });
        }
    }

    /**
     * Gets text from an element after waiting for it to be visible
     */
    public static String getText(final By locator, final WebDriver driver) {
        return ElementWaits.waitForVisibility(locator, driver).getText();
    }

    /**
     * Selects an option from a dropdown by visible text
     */
    public static void selectByText(final By locator, final WebDriver driver, String text) {
        WebElement element = ElementWaits.waitForVisibility(locator, driver);
        new WebDriverWait(driver, RETRY_TIMEOUT)
            .until(d -> {
                try {
                    new Select(element).selectByVisibleText(text);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            });
    }

    /**
     * Checks if an element is displayed
     */
    public static boolean isDisplayed(final By locator, final WebDriver driver) {
        try {
            return ElementWaits.waitForVisibility(locator, driver).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Hovers over an element
     */
    public static void hover(final By locator, final WebDriver driver) {
        WebElement element = ElementWaits.waitForVisibility(locator, driver);
        new WebDriverWait(driver, RETRY_TIMEOUT)
            .until(d -> {
                try {
                    new Actions(d)
                        .moveToElement(element)
                        .pause(Duration.ofMillis(500))
                        .build()
                        .perform();
                    return true;
                } catch (Exception e) {
                    try {
                        ((JavascriptExecutor) d).executeScript(
                            "var event = new MouseEvent('mouseover', {bubbles: true, cancelable: true});" +
                            "arguments[0].dispatchEvent(event);", element);
                        return true;
                    } catch (Exception ex) {
                        return false;
                    }
                }
            });
    }

    /**
     * Waits for page load to complete
     */
    public static void waitForPageLoad(WebDriver driver) {
        new WebDriverWait(driver, PAGE_LOAD_TIMEOUT)
            .until(d -> Objects.equals(((JavascriptExecutor) d)
                    .executeScript("return document.readyState"), "complete"));
    }
}