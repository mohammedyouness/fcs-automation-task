package amazon.gui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import amazon.utils.elements.ElementActions;

public class CartPage {
    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By proceedToCheckoutBtn = By.cssSelector("#sc-buy-box-ptc-button");

    // Actions
    @Step("Proceed to checkout")
    public CheckoutPage proceedToCheckout() {
        ElementActions.click(proceedToCheckoutBtn, driver);
        return new CheckoutPage(driver);
    }

}
