 package com.qa.gorest.tests;

 import com.qa.gorest.base.BaseTest;
 import com.qa.gorest.client.RestClient;
 import com.qa.gorest.constants.APIHttpStatus;
 import io.qameta.allure.Description;
 import org.testng.annotations.BeforeMethod;
 import org.testng.annotations.BeforeTest;
 import org.testng.annotations.Test;

 import java.util.HashMap;
 import java.util.Map;

 import static org.hamcrest.Matchers.*;

 public class GetUserTest extends BaseTest {

     @BeforeMethod //It will work before each and every test
     public void getUserSet(){
         restClient = new RestClient(prop, baseURI);
     }
     //code smell : sonarQube
     //@Description("this test case in progress")//coming from Allure report
     @Test(enabled = true, priority = 3, description = "this test is in progress...")
     public void getAllUsersTest(){
         restClient.get(GOREST_ENDPOINT, true, true)
                 .then().log().all()
                 .assertThat()
                 .statusCode(200);

     }

     @Test(priority = 2)
     public void getUserTest(){
         //restClient = new RestClient(prop, baseURI); //after adding flag in rest client we don't require to create RestClient object for all indivdual Test
         restClient.get(GOREST_ENDPOINT +"/"+7571113, true, true)
                 .then().log().all()
                 .assertThat()
                 .statusCode(APIHttpStatus.OK_200.getCode())
                 .and()
                 .body("id", equalTo(7571113));

     }

     @Test(priority = 1)
     public void getUserWithQueryParamsTest(){
         Map<String, String > queryParams = new HashMap<String, String>();
         queryParams.put("name", "naveen");
         queryParams.put("status", "inactive");
         //RestClient restClient1 = new RestClient(prop, baseURI);
         restClient.get(GOREST_ENDPOINT, queryParams, null, true, true)
                 .then().log().all()
                 .assertThat()
                 .statusCode(APIHttpStatus.OK_200.getCode());
     }

 }
