package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ConfigReader;

public class ParabankApiClient {
    public ParabankApiClient() {
        RestAssured.baseURI = ConfigReader.getProperty("base.api.url");
    }

    public Response getAccountsForCustomer(int customerId) {
        return RestAssured
                .given()
                .accept("application/json")
                .pathParam("customerId", customerId)
                .when()
                .get("/customers/{customerId}/accounts")
                .then()
                .extract()
                .response();
    }

    public Response getAccount(int accountId) {
        return RestAssured
                .given()
                .accept("application/json")
                .pathParam("accountId", accountId)
                .when()
                .get("/accounts/{accountId}")
                .then()
                .extract()
                .response();
    }

    public Response login(String username, String password) {
        return RestAssured
                .given()
                .accept("application/json")
                .pathParam("username", username)
                .pathParam("password", password)
                .when()
                .get("/login/{username}/{password}")
                .then()
                .extract()
                .response();
    }
}
