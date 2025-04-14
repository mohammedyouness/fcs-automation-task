package amazon.gui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import amazon.utils.elements.ElementActions;
import io.qameta.allure.Step;

public class LoginPage {
    private WebDriver driver;
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By emailField = By.id("ap_email_login");
    private final By continueButton = By.id("continue");
    private final By passwordField = By.id("ap_password");
    private final By signInButton = By.id("signInSubmit");

    @Step("Login with credentials")
    public void login(String email, String password) {
        ElementActions.type(emailField, driver, email);
        ElementActions.click(continueButton, driver);
        ElementActions.type(passwordField, driver, password);
        ElementActions.click(signInButton, driver);
    }
}
