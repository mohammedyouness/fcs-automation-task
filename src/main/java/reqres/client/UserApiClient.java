package reqres.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import reqres.config.ApiConfig;
import reqres.models.User;

import static io.restassured.RestAssured.given;

public class UserApiClient {
    
    static {
        RestAssured.baseURI = ApiConfig.BASE_URL;
    }

    public Response createUser(User user) {
        return given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(ApiConfig.USERS_ENDPOINT);
    }

    public Response getUser(int userId) {
        return given()
                .when()
                .get(ApiConfig.USERS_ENDPOINT + "/" + userId);
    }

    public Response updateUser(int userId, User user) {
        return given()
                .contentType("application/json")
                .body(user)
                .when()
                .put(ApiConfig.USERS_ENDPOINT + "/" + userId);
    }
}
