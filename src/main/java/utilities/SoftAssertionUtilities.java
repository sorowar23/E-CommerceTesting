package utilities;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.util.concurrent.TimeUnit;

public final class SoftAssertionUtilities {

    private static SoftAssertions SOFTASSERT;

    public static void instantiateSoftAssert(){
        if(SoftAssertionUtilities.SOFTASSERT == null){
            synchronized ((SoftAssertionUtilities.class))
            {
                if(SoftAssertionUtilities.SOFTASSERT == null)
                {
                    SoftAssertionUtilities.SOFTASSERT = new SoftAssertions();
                }
            }
        }
    }

    private SoftAssertionUtilities()
    {
    }
    public static void assertAll()
    {
        try
        {
            SOFTASSERT.assertAll();
        }
        catch (final Exception e)
        {
           LoggerUtilities.addLog(e, "SoftAssertionUtilities.assertAll");
        }
        finally
        {
            SOFTASSERT = null;
            SOFTASSERT = new SoftAssertions();
        }
    }
    public static void validateResponse(
            final Response getAPIResponse,
            final  int statusCode,
            final int maxResponseTime,
            final boolean softAssertTrue)
    {
        instantiateSoftAssert();
        final Response response = RestUtilities.getGetAPIResponse();
        SOFTASSERT.assertThat(response.getStatusCode()).as("Status Code :").isEqualTo(statusCode);
        SOFTASSERT.assertThat(response.getTimeIn(TimeUnit.MILLISECONDS))
                .as("Response Time :", response.getTimeIn(TimeUnit.MILLISECONDS))
                .isLessThan(maxResponseTime);
        if(softAssertTrue)
        {
            assertAll();
        }
    }

    public static void assertStringEquals(
            final String actualValue,
            final String expectedValue,
            final String description)
    {
        instantiateSoftAssert();
        SOFTASSERT.assertThat(actualValue).as(description).isEqualTo(expectedValue);
    }

    public static void assertStringContains(
            final String actualString,
            final String expectedString,
            final String description)
    {
        instantiateSoftAssert();
        SOFTASSERT.assertThat(actualString).as(description).contains(expectedString);
    }

    public static void assertBooleanEquals(
            final boolean actualValue,
            final boolean expectedValue,
            final String description)
    {
        instantiateSoftAssert();
        SOFTASSERT.assertThat(actualValue).as(description).isEqualTo(expectedValue);
    }
    public static void assertBooleanEquals(
            final String actualValue,
            final boolean expectedValue,
            final String description)
    {
        instantiateSoftAssert();
        final boolean actual = Boolean.parseBoolean(actualValue);
        SOFTASSERT.assertThat(actual).as(description).isEqualTo(expectedValue);
    }
}
