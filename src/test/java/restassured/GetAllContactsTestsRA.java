package restassured;

import com.jayway.restassured.RestAssured;
import dto.AllContactsDto;
import dto.ContactDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetAllContactsTestsRA {

    String token="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoia2F0ZUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY3ODIwNzcyNCwiaWF0IjoxNjc3NjA3NzI0fQ.TI03yiO7r-2r5ZdK9OVINC9UyAKR6ywHg7HxKRUNBPE\n";
    @BeforeMethod
    public void precondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void getAllContactsSuccess(){
        AllContactsDto all = given()
                .header("Authorization", token)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(AllContactsDto.class);
        List<ContactDto> contacts = all.getContacts();
        for(ContactDto contactDto:contacts){
            System.out.println(contactDto.getId());
            System.out.println("***************");
        }
    }
    @Test
    public void getAllContactsNegative(){
        AllContactsDto all = given()
                .header("Authorization", token)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body( "error", equalTo("Unauthorized"))
                .extract()
                .response()
                .as(AllContactsDto.class);
        List<ContactDto> contacts = all.getContacts();
        for(ContactDto contactDto:contacts){
            System.out.println(contactDto.getId());
            System.out.println("***************");
        }
    }



}
