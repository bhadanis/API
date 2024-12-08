package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReqResAPITest extends BaseTest {
    @BeforeMethod //It will work before each and every test
    public void getUserSetUp(){
        restClient = new RestClient(prop, baseURI);
    }

    @Test
    public void getReqResUsersTest(){
        restClient.get(REQRES_ENDPOINT+"/2", false, true)
                .then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getCode());

    }
}
