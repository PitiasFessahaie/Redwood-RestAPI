package apiScript;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import com.jayway.restassured.response.Header;

public class LibraryAPI {

	public List<Header> getHeader(String url, String body, String email, int statue, String content) {

		List<Header> header =

				given().when().get(url).then().assertThat().statusCode(statue).body(body, equalTo(email))
						.body("data[2].avatar", notNullValue()).header("content-type", equalTo(content)).extract()
						.headers().asList();

		return header;
	}

	public String getContent(String url, int statues, String body, String email, String contents) {

		String content =

				given().when().get(url).then().assertThat().statusCode(statues).body(body, equalTo(email))
						.body("data[2].avatar", notNullValue()).header("content-type", equalTo(contents)).extract()
						.header("content-type");

		return content;

	}

	public String getData(String url, int id, int statue, String body, String email) {

		String data = given().param("id", id).when().get(url).then().assertThat().statusCode(statue)
				.body(body, equalTo(email)).extract().body().asString();

		return data;
	}

	public String getString(String User, int statue, String body, String name, String json) {
		String data =

				given().param("id", 3).when().get(User).then().assertThat().statusCode(statue)
						.body("data.avatar", notNullValue()).body(body, equalTo(name)).extract().body().jsonPath()
						.get(json);

		return data;
	}
	public void post(String body,String url,int statue,String ctype) {
		
		given().body(body).when().post(url).then().assertThat().statusCode(statue)
		.header("content-type", equalTo(ctype)).body("id", notNullValue())
		.body("createdAt", notNullValue());
	
	}

}
