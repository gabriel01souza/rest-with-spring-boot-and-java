package br.com.erudio.integrationtests.controller.withxml;

import br.com.erudio.TestConfigs;
import br.com.erudio.integrationtests.vo.AccountCredentialVO;
import br.com.erudio.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerXmlTest {

    private static TokenVO tokenVO;

    @Test
    @Order(0)
    public void testSignin() {
        AccountCredentialVO user = new AccountCredentialVO("leandro", "admin123");

        var accessToken = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class);

        Assert.assertNotNull(tokenVO.getAcessToken());
        Assert.assertNotNull(tokenVO.getRefreshToken());
    }
    @Test
    @Order(1)
    public void testRefresh() {
        AccountCredentialVO user = new AccountCredentialVO("leandro", "admin123");

        var newTokenVO = given()
                .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .pathParam("userName", tokenVO.getUsername())
                .header(TestConfigs.AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
                .when()
                .put("{username}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class);

        Assert.assertNotNull(newTokenVO.getAcessToken());
        Assert.assertNotNull(newTokenVO.getRefreshToken());
    }


}
