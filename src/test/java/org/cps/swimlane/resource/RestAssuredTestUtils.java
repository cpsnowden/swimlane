package org.cps.swimlane.resource;

import static io.restassured.RestAssured.given;

public class RestAssuredTestUtils {

    public static <T> T get(String path, Class<T> responseModel) {
        return given()
                .when().get(path)
                .then()
                .statusCode(200)
                .extract()
                .as(responseModel);
    }

}
