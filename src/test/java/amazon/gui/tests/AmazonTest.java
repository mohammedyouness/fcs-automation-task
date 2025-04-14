package amazon.gui.tests;

import amazon.gui.pages.CartPage;
import amazon.gui.pages.HomePage;
import amazon.gui.pages.LoginPage;
import amazon.gui.pages.ProductPage;
import amazon.utils.core.DriverFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import amazon.utils.files.JsonReader;

@Feature("Amazon Shopping Flow")
public class AmazonTest {
    protected WebDriver driver;
    protected JsonReader testData = new JsonReader("./src/test/resources/testData.json");
    HomePage homePage;
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;
    
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Complete shopping flow test: Login, filter video games, add to cart, and verify checkout")
    public void verifyShoppingFlow() throws InterruptedException {
        homePage.hoverAndClickSignIn()
                .login(testData.getJson("credentials.email"), testData.getJson("credentials.password"));
        Assert.assertTrue(homePage.isUserLoggedIn(),"Failed to login: User should be logged in after entering credentials");

        homePage.
                navigateToProductPage()
                .applyFilters()
                .sortByPriceHighToLow()
                .addProductsBelowPrice(15000)
                .goToCart().proceedToCheckout().addNewAddress(
                        testData.getJson("address.fullName"),
                        testData.getJson("address.phoneNumber"),
                        testData.getJson("address.streetAddress"),
                        testData.getJson("address.building"),
                        testData.getJson("address.city"),
                        testData.getJson("address.district"),
                        testData.getJson("address.nearestLandmark"),
                        testData.getJson("address.addressType"),
                        testData.getJson("address.useAsDefaultAddress"))
                .selectCashOnDelivery();
    }

    @BeforeClass
    public void setUp() {
        DriverFactory.setDriver(DriverFactory.BrowserType.CHROME);
        driver = DriverFactory.getDriver();
        driver.navigate().to(testData.getJson("baseUrl"));
    }

    @BeforeMethod
    public void initializePages() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }
    
    @AfterClass
    public void tearDown() {
         if (driver != null) {
            // driver.quit();
         }
    }

}
