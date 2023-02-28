package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdRA {
    String token="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoia2F0ZUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY3ODIxMDU4MywiaWF0IjoxNjc3NjEwNTgzfQ.ER4M8YWAoZUJi2-hnYoP952VI4ClFC08t3lProVAc4M";
    String id;
    @BeforeMethod
    public void precondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
        int i = new Random().nextInt(1000) + 1000;
        ContactDto dto = ContactDto.builder()
                .name("Alina")
                .lastName("White")
                .email("alina" + i + "@gmail.com")
                .phone("0757645" + i)
                .address("NY")
                .description("Friend")
                .build();
        String message = given()
                .header("Authorization",token)
                .body(dto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");
        String[] all = message.split(": ");
                id=all[1];
    }

    @Test
    public void deleteByIdSuccess(){
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/"+id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
    }

    @Test
    public void deleteByIdWrong(){
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/272fbc56-64bf-491f-aad6-22ebfb1bffc6")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Contact with id: 272fbc56-64bf-491f-aad6-22ebfb1bffc6 not found in your contacts!"));
    }

}
