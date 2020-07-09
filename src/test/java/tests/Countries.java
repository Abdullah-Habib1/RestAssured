package tests;

import java.io.FileOutputStream;
import java.util.HashMap;

import org.testng.annotations.Test;
import org.json.JSONObject;
import org.mozilla.javascript.json.JsonParser;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Countries {


	//Positive Scenario
	@Test(priority = 1)
	public void PositivetestCase() {


		RestAssured.baseURI = "https://restcountries.eu/rest/v2/all?fields=name;capital;currencies;latlng";	

		String Response = RestAssured.given()

				.when()
				.get()
				.then()
				.extract()
				.asString();

		//Use regex to extract the capital name from the string response
		Pattern p = Pattern.compile("(\"capital\":\"(.+?)\")");   // the pattern to search for
		Matcher m = p.matcher(Response);

		
			// we're only looking for one group, so get it
			  String extractedCapitalName = m.group(1);

			// print the group out for verification
			System.out.format("'%s'\n", extractedCapitalName);
	
			//Passing the extracted capital to the capital API and asserting the response
		RestAssured.given()
		        .baseUri("https://restcountries.eu/rest/v2/capital/")
				.when()
				.get("/{extractedCapitalName}",extractedCapitalName)
				.then()
		        .assertThat()
		        .statusCode(200);	

	}	

	
	//Positive Scenario
		@Test(priority = 1)
		public void NegativetestCase() {


			RestAssured.baseURI = "https://restcountries.eu/rest/v2/all?fields=name;capital;currencies;latlng";	

			String Response = RestAssured.given()

					.when()
					.get()
					.then()
					.extract()
					.asString();

			//Use regex to extract the capital name from the string response
			Pattern p = Pattern.compile("(\"capital\":\"(.+?)\")");   // the pattern to search for
			Matcher m = p.matcher(Response);

			
				// we're only looking for one group, so get it
				  String extractedCapitalName = m.group(1);

				// print the group out for verification
				System.out.format("'%s'\n", extractedCapitalName);
		
				//Passing a value that will not be found an hence fail the test case
			RestAssured.given()
			        .baseUri("https://restcountries.eu/rest/v2/capital/")
					.when()
					.get("/{extractedCapitalName}",1234567890)
					.then()
			        .assertThat()
			        .statusCode(200);	

		}	
}

