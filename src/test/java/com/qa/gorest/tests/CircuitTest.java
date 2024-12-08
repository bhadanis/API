package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.JasonPathValidators;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CircuitTest extends BaseTest {

    @BeforeMethod //It will work before each and every test
    public void getUserSetUp(){
        restClient = new RestClient(prop, baseURI);
    }

    @Test
    public void getCircuitTest(){
        Response circuitResponse = restClient.get(CIRCUIT_ENDPOINT+"/2017/circuits.json", false, true);

        circuitResponse.then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getCode());
//        int statusCode = circuitResponse.statusCode();
//        Assert.assertEquals(statusCode, APIHttpStatus.OK_200.getCode());

        JasonPathValidators js = new JasonPathValidators();
        List<String> countryList = js.readList(circuitResponse, "$.MRData.CircuitTable.Circuits[?(@.circuitId == 'shanghai')].Location.country");
        System.out.println("country name : " + countryList);
        Assert.assertTrue(countryList.contains("China"));
        Assert.assertEquals(countryList.get(0), "China");

    }

}
