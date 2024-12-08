package com.qa.gorest.utils;

import com.jayway.jsonpath.PathNotFoundException;
import com.qa.gorest.frameworkexception.APIFrameworkException;
import io.restassured.response.Response;
import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;

public class JasonPathValidators {

    private String getJsonResponseAsString(Response response){
        return response.getBody().asString();
    }

    public <T> T read(Response response, String jsonPath){//T means any type of Return . It could be any Primitive data Type eg. float,double,int, but not List
        String jsonResponse = getJsonResponseAsString(response);
        try {
            return JsonPath.read(jsonResponse, jsonPath);
        } catch (PathNotFoundException e) {
            e.printStackTrace();
            throw new APIFrameworkException(jsonPath + "is not found....");
        }
    }

    public <T> List<T> readList(Response response, String jsonPath){
        try {
            return JsonPath.read(getJsonResponseAsString(response), jsonPath);
        } catch (PathNotFoundException e) {
            e.printStackTrace();
            throw new APIFrameworkException(jsonPath + "is not found....");
        }
    }

    public <T> List<Map<String, T>> readListOfMaps(Response response, String jsonPath){
        try {
            return JsonPath.read(getJsonResponseAsString(response), jsonPath);
        } catch (PathNotFoundException e) {
            e.printStackTrace();
            throw new APIFrameworkException(jsonPath + "is not found....");
        }
    }
}
