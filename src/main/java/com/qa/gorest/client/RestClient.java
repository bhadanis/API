package com.qa.gorest.client;

import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.frameworkexception.APIFrameworkException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.security.PrivateKey;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

//To view all class variables and method need to press ctrl + F12 in intellij and in Ecllipse ctrl + O
//RestClient methods are non static and independent in future if we do parallel thread ans iwe have static then 2nd call need to wait for it. Static create only one copies in memories.
//if we are not maintaining multi threading then we can make Static RestClient Methods
public class RestClient {

//    private static final String BASE_URI = "https://gorest.co.in"; //naming convension of final keyword is capital letter with _ underscore
//    private static final String BEARER_TOKEN = "8275fdee099109110c679733400f3b2b53cf8edba96ecf2bf7cbb629ae5ea981";

    private static RequestSpecBuilder specBuilder;

    private Properties prop;
    private String baseURI;

    private boolean isAuthorizationHeaderAdded = false;
    //Before any method which block executing in Java? static block will be executed before creating object of the class. before main method if we execute any code we need to write in static block.
    // In selenium, we do parallel execution so we need to make sure driver is never static.
    //Assertion should be in Test classes means in Test NG test class

//    static { //we don't need it RA_01
//     specBuilder = new RequestSpecBuilder();
// }

    public RestClient(Properties prop, String baseURI){
        specBuilder = new RequestSpecBuilder();
        this.prop = prop; //after this prop is class variable and after = is constructor variable equal to local variable
        this.baseURI = baseURI;
    }

    public void addAuthorizationHeader(){
        if(!isAuthorizationHeaderAdded) {
            specBuilder.addHeader("Authorization", "Bearer " + prop.getProperty("tokenId"));
            isAuthorizationHeaderAdded = true;
        }
    }

    public void setRequestContentType(String contentType){//json-JSON-Json
        switch(contentType.toLowerCase()){
            case "json" :
                specBuilder.setContentType(ContentType.JSON);
                break;
            case "xml" :
                specBuilder.setContentType(ContentType.XML);
                break;
            case "text" :
                specBuilder.setContentType(ContentType.TEXT);
                break;
            case "multipart" :
                specBuilder.setContentType(ContentType.MULTIPART);
                break;
            case "html" :
                specBuilder.setContentType(ContentType.HTML);
                break;

            default:
                System.out.println("Please pass the right content type...");
                throw new APIFrameworkException("INVALIDCONTENTTYPE");
        }
    }

    //spec methods
    private RequestSpecification createRequestSpec(boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if (includeAuth){addAuthorizationHeader();}
        return specBuilder.build();

    }
    //Overloading : same method name with different parameter
    private RequestSpecification createRequestSpec(Map<String, String> headersMap, boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if (includeAuth){addAuthorizationHeader();}
        if(headersMap !=null){
            specBuilder.addHeaders(headersMap);
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, Object> queryParams, boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if(includeAuth){addAuthorizationHeader();}
        if(headersMap !=null){
            specBuilder.addHeaders(headersMap);
        }
        if(queryParams !=null){
            specBuilder.addQueryParams(queryParams);
        }
        return specBuilder.build();

    }

    private RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if(includeAuth){addAuthorizationHeader();}
        setRequestContentType(contentType);
        if(requestBody!=null){
            specBuilder.setBody(requestBody);
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, boolean includeAuth){
        RequestSpecBuilder requestSpec = specBuilder.setBaseUri(baseURI);
        if(includeAuth){addAuthorizationHeader();}
        setRequestContentType(contentType);
        if(headersMap !=null){
            specBuilder.addHeaders(headersMap);
        }
        if(requestBody!=null){
            specBuilder.setBody(requestBody);
        }
        return specBuilder.build();
    }

    //Http Methods Utils:
    //We are using encapsulation here above methods are calling by get call which are private in nature but get is public so every one can call it.
    //GET Methods
    public Response get(String serviceUrl, boolean includeAuth, boolean log){
        if(log) {
            return RestAssured.given(createRequestSpec(includeAuth)).log().all()
                    .when()
                        .get(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(includeAuth)).when().get(serviceUrl);
    }

    public Response get(String serviceUrl, Map<String, String> headersMap, boolean includeAuth, boolean log){
        if(log) {
            return RestAssured.given(createRequestSpec(headersMap, includeAuth)).log().all()
                    .when()
                        .get(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(headersMap, includeAuth)).when().get(serviceUrl);
    }
    public Response get(String serviceUrl, Map<String, String> headersMap, Map<String, Object> queryParams, boolean includeAuth, boolean log){
        if(log) {
            return RestAssured.given(createRequestSpec(headersMap, queryParams, includeAuth)).log().all()
                    .when()
                        .get(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(headersMap, queryParams, includeAuth)).when().get(serviceUrl);
    }

    //POST Methods
    public Response post(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log){
        if (log){
            return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
                    .when()
                    .post(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().post(serviceUrl);
    }

    public Response post(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log){
        if (log){
            return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
                    .when()
                    .post(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().post(serviceUrl);
    }

    //PUT Methods
    public Response put(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log){
        if (log){
            return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
                    .when()
                    .put(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().put(serviceUrl);
    }

    public Response put(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log){
        if (log){
            return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
                    .when()
                    .put(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().put(serviceUrl);
    }

    //PUT Methods
    public Response patch(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log){
        if (log){
            return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
                    .when()
                    .patch(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().patch(serviceUrl);
    }

    public Response patch(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log){
        if (log){
            return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
                    .when()
                    .patch(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().patch(serviceUrl);
    }

    //DELETE Methods
    public Response delete(String serviceUrl, boolean includeAuth, boolean log){
        if (log){
            return RestAssured.given(createRequestSpec(includeAuth)).log().all()
                    .when()
                    .delete(serviceUrl);
        }
        return RestAssured.given(createRequestSpec(includeAuth)).when().delete(serviceUrl);
    }

    //Oauth 2.0 : all parameter passes from the .xml file
    public String getAccessToken(String serviceURL, boolean includeAuth, String grantType, String clientId, String clientSecret){
        //1. POST-Get the access token

        String accessToken = given(createRequestSpec(includeAuth)).log().all()
                //.contentType(ContentType.URLENC)
                .header("Content-Type", ContentType.URLENC)//"application/x-www-form-urlencoded"
                .formParam("grant_type", grantType)// line 16, 17, 18 provided by web side when login
                .formParam("client_id", clientId)//"m8r8KcDWi4VrEp1tICMBtaibL9K9AQ1H"
                .formParam("client_secret", clientSecret).//"vCOX7iWxP9rQMzKJ"
        when()
                .post(serviceURL).//"/v1/security/oauth2/token"
        then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getCode())
                .extract().path("access_token");
        System.out.println("accessToken --> " + accessToken);
        return accessToken;
    }

}
