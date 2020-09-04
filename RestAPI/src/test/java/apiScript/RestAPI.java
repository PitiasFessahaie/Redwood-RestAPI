package apiScript;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Iterator;
import java.util.List;

import org.testng.annotations.Test;

import com.jayway.restassured.response.Header;

public class RestAPI {

	String BaseURL = "https://reqres.in/api/";
	String User = BaseURL + "users";
	String Register = BaseURL + "users/register";
	String Login = BaseURL + "users/login";
	LibraryAPI lib;
	String data3;
	String text;

	@Test(enabled = false)
	public void getHeader() {
		// Ctrl + Space
		lib = new LibraryAPI();

		List<Header> header = lib.getHeader(User, "data[2].email", "emma.wong@reqres.in", 200,
				"application/json; charset=utf-8");

		Iterator<Header> itr = header.iterator();
		while (itr.hasNext()) {
			Header data = itr.next();
			System.out.println(data);

		}

	}

	@Test(enabled = false)

	public void getContentType() {

		String data = given().when().get(User).then().assertThat().statusCode(200)
				.body("data[2].email", equalTo("emma.wong@reqres.in")).body("data[2].avatar", notNullValue())
				.header("content-type", equalTo("application/json; charset=utf-8")).extract().header("content-type");

		System.out.println("The Content type is " + data);

	}

	@Test(enabled = false)
	public void getThirdData() {
		// Get the data and validate the email

		try {
			data3 = lib.getData(User, 3, 200, "data.email", "emma.wong@reqres.in");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println(data3);
	}

	@Test(enabled = false)
	public void getEmmaText() {
		try {
			text = lib.getString(User, 200, "data.first_name", "Emma", "ad.text");

		} catch (Exception e)

		{
			System.out.println(e.getMessage());
		}

		System.out.println("Emma Text: " + text);

	}

	@Test(enabled = false)
	public void postData() {

		String body = "{'name':'me','job':'qa'}"; // Data
		try {
			lib.post(body, User, 201, "application/json; charset=utf-8");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Test(enabled = true)
	public void extractAndLoginTest() {

		// Extract a Person Register,SignIn and Verify

		String body = "{'email': 'sydney@fife','password': 'pistol'}";
		String body1 = "{'email':'peter@kleven','password': 'cityslika'}";

		// or
		String email = given().param("id", 3).when().get(User).then().assertThat().statusCode(200)
				.body("data.email", equalTo("emma.wong@reqres.in")).extract().body().jsonPath().getString("data.email");

		String LoginBody = "{'email':'" + email + "','password':'bond'}"; // Caution ???
		String registerBody = "{'email':'" + email + "'}";
		
		System.out.println(LoginBody);
		System.out.println(registerBody);

		given().body(registerBody).when().post(Register).then().assertThat().statusCode(201);

		given().body(LoginBody).when().post(Login).then().assertThat().statusCode(201);

		// Refactoring Code Next ???

	}

}
