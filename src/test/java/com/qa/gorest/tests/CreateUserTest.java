package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

 public class CreateUserTest extends BaseTest {

     @BeforeMethod //It will work before each and every test
     public void getUserSetUp(){
         restClient = new RestClient(prop, baseURI);
     }

     @DataProvider
     public Object[][] getUserTestData(){
         return new Object[][]{
                 {"Data", "male", "active"},
                 {"Enum", "female", "inactive"},
                 {"Class", "male", "active"}
         };
     }

    @DataProvider
    public Object [][] getUserTestSheetData(){
        return ExcelUtil.getTestData(APIConstants.GOREST_USER_SHEET_NAME);
    }

     @Test(dataProvider = "getUserTestSheetData")
     public void createUserTest(String name, String gender, String status){
         //1. POST:
         User user = new User(name, StringUtils.getRandomEmailIDWithTimeMillis(), gender, status);
         Integer userId =  restClient.post(GOREST_ENDPOINT, "json", user,true, true)
                 .then()
                 .assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
                 .extract().path("id");
         System.out.println("User Id : " + userId);

         //2. GET:
         RestClient restClientGet = new RestClient(prop, baseURI);
         restClientGet.get(GOREST_ENDPOINT+"/"+userId, true, true)
                 .then().log().all()
                 .assertThat().statusCode(APIHttpStatus.OK_200.getCode())
                 .and().body("id", equalTo(userId));
     }

 }
