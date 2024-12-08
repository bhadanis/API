package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AmadeusAPITest extends BaseTest {

    private String accessToken;

    @Parameters({"baseURI", "grantType", "clientId", "clientSecret"})
    @BeforeMethod //It will work before each and every test
    public void flightAPISetUp(String baseURI, String grantType, String clientId, String clientSecret){
        restClient = new RestClient(prop, baseURI);
        accessToken = restClient.getAccessToken(AMADEUS_TOKEN_ENDPOINT, false, grantType, clientId, clientSecret);

    }

    @Test
    public void getFlightInfoTest() {
        //2. get flight info : GET
        RestClient restClientFlight = new RestClient(prop, baseURI);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("origin", "PAR");
        queryParams.put("maxPrice", 200);

        Map<String, String > headerMap = new HashMap<String, String>();
        headerMap.put("Authorization", "Bearer " + accessToken);

        Response flightDataResponse = restClientFlight.get(AMADEUS_ENDPOINT, headerMap, queryParams,false, true).
        then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getCode())
                .and()
                .extract()
                .response();

        JsonPath js = flightDataResponse.jsonPath();
        String type = js.get("data[0].type");
        System.out.println(type);//flight destination

    }
}
