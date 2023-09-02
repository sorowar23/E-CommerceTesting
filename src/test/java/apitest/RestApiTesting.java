package apitest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import endpoints.UserHelper;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.*;

import java.io.IOException;
import java.util.Map;

@Listeners(TestListener.class)
public class RestApiTesting {
	
	private WebDriver driver;

    @BeforeClass
    public void setUp()  {
      ConfigureDefaultProperties.setUpDefaultProperties(RestApiTesting.class);
    }

    @Test(priority = 1)
    public void postData() throws JsonProcessingException, IOException
    {
        Logger LOG = LoggerFactory.getLogger(LoggerUtilities.class);
        LoggerUtilities.addInfoLog("Test Start From Here...");
        Response responseApi = null;
        Response getResponseApi = null;
       // String testData = FileReaderUtilities.readTextFromFileInResource("testdata/sampleInput.json");
      //  System.out.println("Output data::"+ testData);

        JsonNode testData = FileReaderUtilities.readAllNodesFromJsonFile("testdata/sampleInput.json");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> testDataMap = objectMapper.convertValue(testData, new TypeReference<Map<String, Object>>(){});
        testDataMap.put("tagId", 10);
        testDataMap.put("tagType", "ACTIVE");
        //System.out.println("Test Data as Json:::"+ testData);
        System.out.println("Test Data as Map:::"+ testDataMap);

        //for (String key : testDataMap.keySet()) {
          //  System.out.println(key + " : " + testDataMap.get(key));
        //}
        String testData1 = testData.toString();

        responseApi = RestUtilities.apiPostAPIRequest(testData1, UserHelper.postUserRecord(), 201, 5000, true);
        LoggerUtilities.addInfoLog(responseApi.asPrettyString());

       // String Id = responseApi.body().jsonPath().getString("id");
       // System.out.println("ID:"+Id);
        getResponseApi = RestUtilities.apiRequestWithoutParam("GET", UserHelper.getUserRecords(), 200, 1000, true);
        
        LoggerUtilities.addInfoLog(getResponseApi.asPrettyString());
        
        JsonNode actualObj = objectMapper.readTree(getResponseApi.asPrettyString());
        Map<String, Object> testDataMap1 = objectMapper.convertValue(actualObj, new TypeReference<Map<String, Object>>(){});
        System.out.println("Response Out put:"+ testDataMap1);
   
       
    }
    
   
}
