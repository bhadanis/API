package com.qa.gorest.utils;

public class StringUtils {

    public static String getRandomEmailIDWithTimeMillis(){
        return "api"+System.currentTimeMillis()+"@api.com";
    }
}
