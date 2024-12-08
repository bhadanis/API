package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class APISchemaValidationTest extends BaseTest {

    @BeforeMethod //It will work before each and every test
    public void getUserSetUp(){
        restClient = new RestClient(prop, baseURI);
    }

    @Test
    public void createUserAPISchemaTest(){
        //1. POST:
        User user = new User("Tom", StringUtils.getRandomEmailIDWithTimeMillis(), "male", "active");
        restClient.post(GOREST_ENDPOINT, "json", user,true, true)
                .then().log().all()
                .assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
                .and()
                .body(matchesJsonSchemaInClasspath("createuserschema.json"));

    }


}
