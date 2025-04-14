package amazon.gui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import amazon.utils.elements.ElementActions;

public class HomePage {
    private WebDriver driver;
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By accountListsBtn = By.id("nav-link-accountList-nav-line-1");
    private final By signInBtn = By.cssSelector("a[data-nav-ref='nav_signin']");
    private final By allMenuBtn = By.cssSelector("a#nav-hamburger-menu");
    private final By seeAllBtn = By.cssSelector("a.hmenu-compressed-btn");
    private final By videoGamesCategory = By.xpath("//div[contains(text(),'Video Games')]");
    private final By allVideoGamesLink = By.xpath("//a[contains(text(),'All Video Games')]");

    // Actions
    @Step("Hover and click sign in")
    public LoginPage hoverAndClickSignIn() {
        ElementActions.hover(accountListsBtn, driver);
        ElementActions.click(signInBtn, driver);
        return new LoginPage(driver);
    }

    @Step("Navigate to product page")
    public ProductPage navigateToProductPage() {
        ElementActions.click(allMenuBtn, driver);
        ElementActions.click(seeAllBtn, driver);
        ElementActions.click(videoGamesCategory, driver);
        ElementActions.click(allVideoGamesLink, driver);
        return new ProductPage(driver);
    }

    @Step("Check if user is logged in")
    public boolean isUserLoggedIn() {
        String accountText = ElementActions.getText(accountListsBtn, driver);
        return !accountText.toLowerCase().contains("sign in");
    }

}
