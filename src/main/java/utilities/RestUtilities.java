package utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public final class RestUtilities {

    private RestUtilities() 
    {
    }

    private static Response GETAPIRESPONSE;
    private static String POST = "POST";
    private static RequestSpecification REQ;
    private static int INTCOUNT;
    private static final  Charset CHARSET = StandardCharsets.UTF_8;
    private static ByteArrayOutputStream INPUTREQUEST = new ByteArrayOutputStream();
    private static ByteArrayOutputStream OUTPUTREQUEST = new ByteArrayOutputStream();
    
    private static PrintStream INPUT;
    private static PrintStream OUTPUT;
    
    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String PUT_METHOD = "PUT";
    private static final String DELETE_METHOD = "DELETE";
    private static Properties PROP = new Properties();

    public static Response getGetAPIResponse() {
        return GETAPIRESPONSE;
    }

    public static void setGetAPIResponse(final Response getAPIResponse) 
    {
        GETAPIRESPONSE = getAPIResponse;
    }

    private static Response performCall(final RequestSpecification req, String httpMethod, final String endPoint) 
    {
        GETAPIRESPONSE = null;
        
        if (httpMethod.equalsIgnoreCase(GET_METHOD)) 
        {
            GETAPIRESPONSE = given().spec(req).when().get(endPoint);
        }
        else if(httpMethod.equalsIgnoreCase(POST_METHOD)) 
        {
        	GETAPIRESPONSE = given().spec(req).when().post(endPoint);
        }
        RestUtilities.setGetAPIResponse(GETAPIRESPONSE);
        return GETAPIRESPONSE;
    }
    
    public static RequestSpecification createReqSpecificationWithFilter()
    {
		
    	try {
    		
    		INPUT = new PrintStream(INPUTREQUEST, true, CHARSET.name());
    		OUTPUT = new PrintStream(OUTPUTREQUEST, true, CHARSET.name());
    		REQ = new RequestSpecBuilder().setBaseUri(GeneralUtilities.getApiBaseURI())
    				.setRelaxedHTTPSValidation()
    				.addFilter(RequestLoggingFilter.logRequestTo(INPUT))
    				.addFilter(RequestLoggingFilter.logRequestTo(OUTPUT))
    				.setContentType(ContentType.JSON)
    				.build();
    		
    	}catch (Exception e) {
			LoggerUtilities.addLog(e, "createReqSpecAddingHeader();");
		}
    	
    	return REQ;
	}
    
    public static RequestSpecification createReqSpecAddingHeader(final boolean authInQryOaram)
    {
    	REQ = createReqSpecificationWithFilter();
    	
    	if(authInQryOaram) 
    	{
    		REQ = REQ.queryParam("Authorization", "Bearer");
    		
    	}
    	else
    	{
    		REQ = REQ.header("Authorization", "Bearer");
    	}
    	
    	return REQ;
    }

    public static Response apiPostAPIRequest(
            final String jsonString,
            final String endPoints,
            final int statusCode,
            final int maxResponseTime,
            final boolean softAssertTrue) {
    	
        return detaileRestCall(POST, endPoints, "", "", "", "", jsonString, statusCode, maxResponseTime, softAssertTrue, false);
    }

    public static Response apiRequestUsingPathParam(
            final String httpMethod,
            final String endPoint,
            final String paramName,
            final String paramValue,
            final int statusCode,
            final int maxResponseTime,
            final boolean softAssertTrue) {
        return detaileRestCall(httpMethod, endPoint, paramName, paramValue, "", "", null, statusCode, maxResponseTime, softAssertTrue, false);
    }
    
    public static Response apiRequestWithoutParam(
            final String httpMethod,
            final String endPoint,
            final int statusCode,
            final int maxResponseTime,
            final boolean softAssertTrue) {
        return detaileRestCall(httpMethod, endPoint, "", "", "", "", null, statusCode, maxResponseTime, softAssertTrue, false);
    }

    public static Response detaileRestCall(
    		final String httpMethod,
    		final String endPoints,
    		final String pathParamName,
    		final String pathParamValue,
    		final String queryParamName,
    		final String queryParamValue,
    		final String body,
    		final int statusCode,
    		final int maxResponseTime,
    		final boolean softAssertTrue,
    		final boolean authInQryParam) 
    {

    	try 
    	{
    		INTCOUNT = 1;
    		REQ = createReqSpecAddingHeader(authInQryParam);
    		
    		
    		//GETAPIRESPONSE = performCall(REQ, httpMethod, endPoints);
    		
    		if( !( "".equals(pathParamName))  ) 
    		{
    			REQ = REQ.pathParam(pathParamName, pathParamName);
    		}
    		
    		
    		if( !( "".equals(queryParamName))  ) 
    		{
    			REQ = REQ.pathParam(queryParamName, queryParamValue);
    		}
    		
    		if( body != null ) 
    		{
    			REQ = REQ.body(body);
    			//System.out.println("BODY22::"+ REQ.toString());
    		}
    		
    		if (!softAssertTrue) 
    		{
    			System.out.println("Log Request and Response");
    		}
    		
    		Thread.sleep(500);
			GETAPIRESPONSE = performCall(REQ, httpMethod, endPoints);
    		
    		//System.out.println("OUTPUT:::"+ GETAPIRESPONSE.asPrettyString());

    		

    		SoftAssertionUtilities.validateResponse(GETAPIRESPONSE, statusCode, maxResponseTime, softAssertTrue);

    	} 
    	catch (Exception exception) 
    	{
    		exception.printStackTrace();
    	}

    	return GETAPIRESPONSE;
    }
}
