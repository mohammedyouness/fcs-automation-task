package reqres.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import reqres.client.UserApiClient;
import reqres.models.User;

import static org.testng.Assert.*;

public class APITests {
    private UserApiClient userApiClient;
    private User testUser;
    private final Integer KNOWN_USER_ID = 2;

    // Note: We are using a known existing user ID from "reqres.in" in the update flow, rather than the ID returned from the user creation step.
    // This is because reqres.in does not persist data. As a result, any user created via a POST request cannot be retrieved or updated later using its ID, since it is not actually stored on the server.

    @BeforeClass
    public void setup() {
        userApiClient = new UserApiClient();
        testUser = new User();
        testUser.setFirstName("Janet");
        testUser.setLastName("Weaver");
        testUser.setEmail("janet.weaver@reqres.in");
    }

    @Test(priority = 1)
    @Description("Create a new user and verify the response")
    public void verifyCreatingANewUser() {
        try {
            Response response = userApiClient.createUser(testUser);

            assertEquals(response.getStatusCode(), 201, "Unexpected status code during user creation.");
            User createdUser = response.as(User.class);
            assertNotNull(createdUser.getId(), "User ID should not be null after creation.");
        } catch (Exception e) {
            fail("Exception during user creation: " + e);
        }
    }

    @Test(priority = 2)
    @Description("Retrieve an existing user and verify the details")
    public void verifyExistingUserDetailsOnRetrieval() {
        try {
            Response response = userApiClient.getUser(KNOWN_USER_ID);

            assertEquals(response.getStatusCode(), 200, "Unexpected status code while retrieving user.");
            User retrievedUser = response.jsonPath().getObject("data", User.class);

            assertNotNull(retrievedUser, "Retrieved user data is null.");
            assertEquals(retrievedUser.getId(), KNOWN_USER_ID);
            assertEquals(retrievedUser.getFirstName(), "Janet");
            assertEquals(retrievedUser.getLastName(), "Weaver");
            assertEquals(retrievedUser.getEmail(), "janet.weaver@reqres.in");
            assertNotNull(retrievedUser.getAvatar(), "Avatar should not be null.");
        } catch (Exception e) {
            fail("Exception during user retrieval: " + e);
        }
    }

    @Test(priority = 3)
    @Description("Update user details and verify the changes")
    public void verifyUserUpdateReflectsCorrectChanges() {
        try {
            testUser.setEmail("janet.weaver.updated@reqres.in");
            Response response = userApiClient.updateUser(KNOWN_USER_ID, testUser);

            assertEquals(response.getStatusCode(), 200, "Unexpected status code during user update.");
            User updatedUser = response.as(User.class);
            assertEquals(updatedUser.getEmail(), testUser.getEmail(), "Updated email doesn't match the expected value.");
        } catch (Exception e) {
            fail("Exception during user update: " + e);
        }
    }
}
