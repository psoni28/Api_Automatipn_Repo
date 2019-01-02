package testcasesAPI;

import static org.testng.Assert.fail;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherAPITest {

	@Test(enabled = false)
	public void verifyBodyWeatherAPI() {
		RestAssured.baseURI = "https://samples.openweathermap.org/data/2.5";

		RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation();
		Response response = httpRequest.request(Method.GET,
				"/group?id=524901,703448,2643743&units=metric&appid=b6907d289e10d714a6e88b30761fae22");

		String body = response.getBody().asString();

		System.out.println(body);
	}

	@Test(enabled = false)
	public void verifyStatusCodeWeatherAPI() {
		RestAssured.baseURI = "https://samples.openweathermap.org/data/2.5";

		RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation();
		Response response = httpRequest.request(Method.GET,
				"/group?id=524901,703448,2643743&units=metric&appid=b6907d289e10d714a6e88b30761fae22");

		int actualStatusCode = response.getStatusCode();

		Assert.assertEquals(actualStatusCode, 200, "status code is not matched");
		Reporter.log("Status code is matched");
	}

	@Test(enabled = false)
	public void verifyInvalidStatusCodeWeatherAPI() {
		RestAssured.baseURI = "https://samples.openweathermap.org/data/2.5";

		RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation();
		Response response = httpRequest.request(Method.GET, "/weather");

		int actualStatusCode = response.getStatusCode();
		System.out.println(actualStatusCode);
		Assert.assertNotEquals(actualStatusCode, 200, "status code is matched");
		Reporter.log("Status code is not matched");
	}

	@Test(enabled = false)
	public void verifStatusCodeLineWeatherAPI() {
		RestAssured.baseURI = "https://samples.openweathermap.org/data/2.5";

		RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation();
		Response response = httpRequest.request(Method.GET,
				"/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");

		String statusCodeLine = response.getStatusLine();
		System.out.println(statusCodeLine);
		Assert.assertEquals(statusCodeLine, "HTTP/1.1 200 OK", "status line is not matched");
		Reporter.log("Status line is matched");
	}

	@Test(enabled = false)
	public void verifyWeatherAPIHeaders() {
		RestAssured.baseURI = "https://samples.openweathermap.org/data/2.5";

		RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation();
		Response response = httpRequest.request(Method.GET,
				"/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");

		// Header named Content-Type
		String contentType = response.header("Content-Type");
		System.out.println("Content-Type value: " + contentType);

		// Reader header of a give name. In this line we will get
		// Header named Server
		String serverType = response.header("Server");
		System.out.println("Server value: " + serverType);

		// Reader header of a give name. In this line we will get
		// Header named Content-Encoding
		String acceptLanguage = response.header("Content-Encoding");
		System.out.println("Content-Encoding: " + acceptLanguage);

		Assert.assertTrue(contentType.contains("application/json"), "content type is not matched");
		Assert.assertTrue(serverType.contains("openresty/1.9.7.1"), "server is not matched");
		Assert.assertTrue(acceptLanguage.contains("gzip"), "language is not matched");

	}

	@Test(enabled = false)
	public void verifyWeatherAPIBodyValue() {
		RestAssured.baseURI = "https://samples.openweathermap.org/data/2.5";

		RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation();
		Response response = httpRequest.request(Method.GET,
				"/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");

		JsonPath jsonPathEvaluator = response.jsonPath();
		String base = jsonPathEvaluator.get("base");
		Assert.assertTrue(base.contains("stations"), "base is not matched");

	}

	// post request
	@Test(enabled = true)
	public void verifyUserCreateAPIPostRequest() {
		RestAssured.baseURI = "https://reqres.in";

		RequestSpecification httpRequest = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "morpheus");
		requestParams.put("job", "leader");

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());

		Response response = httpRequest.request(Method.POST, "/api/users");

		int statusCode = response.getStatusCode();

		Assert.assertTrue(statusCode == 201, "status code is not matched");

	}
}
