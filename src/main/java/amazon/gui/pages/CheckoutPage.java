package amazon.gui.pages;

import amazon.utils.elements.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By addAddressBtn = By.xpath("//span[contains(@id, 'add-new-address')]");
    private final By fullNameField = By.id("address-ui-widgets-enterAddressFullName");
    private final By mobileNumberField = By.id("address-ui-widgets-enterAddressPhoneNumber");
    private final By streetAddressField = By.id("address-ui-widgets-enterAddressLine1");
    private final By buildingField = By.id("address-ui-widgets-enter-building-name-or-number");
    private final By cityField = By.id("address-ui-widgets-enterAddressCity");
    private final By districtField = By.id("address-ui-widgets-enterAddressDistrictOrCounty");
    private final By nearestLandmarkField = By.id("address-ui-widgets-landmark");
    private final By homeAddressType = By.cssSelector("input#address-ui-widgets-addr-details-res-radio-input");
    private final By officeAddressType = By.cssSelector("input#address-ui-widgets-addr-details-com-radio-input");
    private final By useAsDefaultAddressCheckbox = By.cssSelector("input#address-ui-widgets-use-as-my-default");
    private final By useAddressBtn = By.xpath("//input[@data-testid='bottom-continue-button']");
    private final By cashOnDeliveryOption = By.xpath("//span[text()='Cash on Delivery (COD)']/parent::span/preceding-sibling::input");
    private final By useThisPaymentBtn = By.xpath("//input[@data-testid='bottom-continue-button']");

    // Actions
    @Step("Add new address")
    public CheckoutPage addNewAddress(String fullName, String phoneNumber, String streetAddress,
                              String building, String city, String district, String nearestLandmark, String addressType, String useAsDefaultAddress) throws InterruptedException {
        ElementActions.click(addAddressBtn, driver);
        ElementActions.type(fullNameField, driver, fullName);
        ElementActions.type(mobileNumberField, driver, phoneNumber);
        ElementActions.typeAndEnterEsc(streetAddressField, driver, streetAddress);
        ElementActions.type(buildingField, driver, building);
        ElementActions.typeAndEnterEsc(cityField, driver, city);
        ElementActions.type(districtField, driver, district);
        ElementActions.type(nearestLandmarkField, driver, nearestLandmark);
        if (addressType.equalsIgnoreCase("home")) {
            ElementActions.click(homeAddressType, driver);
        }
        ElementActions.click(officeAddressType, driver);
        if (Boolean.parseBoolean(useAsDefaultAddress)) {
            ElementActions.click(useAsDefaultAddressCheckbox, driver);
        }
        ElementActions.type(districtField, driver, district);
        ElementActions.click(useAddressBtn, driver);
        return this;
    }

    @Step("Select cash on delivery payment")
    public void selectCashOnDelivery() {
        ElementActions.click(cashOnDeliveryOption, driver);
        ElementActions.click(useThisPaymentBtn, driver);
    }
}
