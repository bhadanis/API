package com.qa.gorest.frameworkexception;

public class APIFrameworkException extends RuntimeException{

    public APIFrameworkException(String mesg){ //constructure of the class with parameter
        super(mesg); //we need to call parent class constructure so we need to use Super keyword
    }
}
