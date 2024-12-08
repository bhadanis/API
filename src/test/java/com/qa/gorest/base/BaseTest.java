package com.qa.gorest.base;

import com.qa.gorest.client.RestClient;
import com.qa.gorest.configuration.ConfigurationManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.Properties;

public class BaseTest {

    //Service URLs:
    public static final String GOREST_ENDPOINT =  "/public/v2/users";
    public static final String REQRES_ENDPOINT = "/api/users";
    public static final String CIRCUIT_ENDPOINT = "/api/f1";
    public static final String AMADEUS_TOKEN_ENDPOINT = "/v1/security/oauth2/token";
    public static final String AMADEUS_ENDPOINT = "/v1/shopping/flight-destinations";

    protected ConfigurationManager config;
    protected Properties prop;
    protected RestClient restClient;
    protected String baseURI;

    @Parameters({"baseURI"})
    @BeforeTest
    public void setUp(String baseURI){

        RestAssured.filters(new AllureRestAssured());// need to go to cmd and go to project path and write allure serve allure-results

    config = new ConfigurationManager();
    prop = config.intiProp();
    this.baseURI = baseURI;
    //String baseURI = prop.getProperty("baseURI");
    //restClient = new RestClient(prop,baseURI); // bz created in Test class by using @BeforeMethod annotation to solve problem in Get user class
    }


}
