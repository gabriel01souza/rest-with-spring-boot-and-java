package br.com.erudio.integrationtests.controller.withyaml;

import br.com.erudio.TestConfigs;
import br.com.erudio.integration.testcontainers.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.AccountCredentialVO;
import br.com.erudio.integrationtests.vo.PersonVO;
import br.com.erudio.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.DataInput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static YAMLMapper objectMapper;

    private static PersonVO person;

    @BeforeAll
    public static void setup() {
        objectMapper = new YAMLMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonVO();
    }

    @Test
    @Order(0)
    public void authorizarion() throws JsonMappingException, JsonProcessingException {
        AccountCredentialVO user = new AccountCredentialVO("leandro", "admin123");

        var accessToken = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(
                                        EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
                                )
                )
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(user, (ObjectMapper) objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class,(ObjectMapper) objectMapper)
                .getAcessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

    }

    @Test
    @Order(1)
    public void testCreate() throws IOException {
        person.setLastName("Piquet Souto Maior");

        var persistedPerson = given().spec(specification).config(
                        RestAssuredConfig.config()
                                .encoderConfig(
                                        EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
                                )
                )
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .body(person, (ObjectMapper) objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonVO.class, (ObjectMapper) objectMapper);

        person = persistedPerson;

        Assertions.assertNotNull(persistedPerson);

        Assertions.assertNotNull(persistedPerson.getId());
        Assertions.assertNotNull(persistedPerson.getFirstName());
        Assertions.assertNotNull(persistedPerson.getLastName());
        Assertions.assertNotNull(persistedPerson.getAddress());
        Assertions.assertNotNull(persistedPerson.getGender());
        Assertions.assertTrue(persistedPerson.getEnabled());

        assertTrue(persistedPerson.getId() > 0);

        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(2)
    public void testUpdate() throws IOException {
        person.setLastName("Piquet Souto Maior");

        var persistedPerson = given().spec(specification).config(
                        RestAssuredConfig.config()
                                .encoderConfig(
                                        EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
                                )
                )
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .body(person,(ObjectMapper) objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonVO.class, (ObjectMapper) objectMapper);

        person = persistedPerson;

        Assertions.assertNotNull(persistedPerson);

        Assertions.assertNotNull(persistedPerson.getId());
        Assertions.assertNotNull(persistedPerson.getFirstName());
        Assertions.assertNotNull(persistedPerson.getLastName());
        Assertions.assertNotNull(persistedPerson.getAddress());
        Assertions.assertNotNull(persistedPerson.getGender());
        Assertions.assertTrue(persistedPerson.getEnabled());

        assertTrue(persistedPerson.getId() > 0);

        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(3)
    public void testDisablePersonById() throws IOException {
        mockPerson();

        var persistedPerson = given().spec(specification).config(
                        RestAssuredConfig.config()
                                .encoderConfig(
                                        EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
                                )
                )
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonVO.class, (ObjectMapper) objectMapper);

        person = persistedPerson;

        Assertions.assertNotNull(persistedPerson);

        Assertions.assertNotNull(persistedPerson.getId());
        Assertions.assertNotNull(persistedPerson.getFirstName());
        Assertions.assertNotNull(persistedPerson.getLastName());
        Assertions.assertNotNull(persistedPerson.getAddress());
        Assertions.assertNotNull(persistedPerson.getGender());
        Assertions.assertFalse(persistedPerson.getEnabled());

        Assertions.assertEquals(person.getId(), persistedPerson.getId());

        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(4)
    public void testFindById() throws IOException {
        mockPerson();

        var persistedPerson = given().spec(specification).config(
                        RestAssuredConfig.config()
                                .encoderConfig(
                                        EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
                                )
                )
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonVO.class, (ObjectMapper) objectMapper);

        person = persistedPerson;

        Assertions.assertNotNull(persistedPerson);

        Assertions.assertNotNull(persistedPerson.getId());
        Assertions.assertNotNull(persistedPerson.getFirstName());
        Assertions.assertNotNull(persistedPerson.getLastName());
        Assertions.assertNotNull(persistedPerson.getAddress());
        Assertions.assertNotNull(persistedPerson.getGender());
        Assertions.assertFalse(persistedPerson.getEnabled());

        Assertions.assertEquals(person.getId(), persistedPerson.getId());

        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(5)
    public void testDelete() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        given().spec(specification).config(
                        RestAssuredConfig.config()
                                .encoderConfig(
                                        EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
                                )
                )
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .pathParam("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204)
                .extract()
                .body()
                .asString();
    }

    @Test
    @Order(6)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {

        var content = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonVO[].class, (ObjectMapper) objectMapper);

        List<PersonVO> people = Arrays.asList(content);

        PersonVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());
        assertFalse(foundPersonOne.getEnabled());


        assertEquals(1, foundPersonOne.getId());

        assertEquals("Ayrton", foundPersonOne.getFirstName());
        assertEquals("Senna", foundPersonOne.getLastName());
        assertEquals("São Paulo", foundPersonOne.getAddress());
        assertEquals("Male", foundPersonOne.getGender());

        PersonVO foundPersonSix = people.get(5);

        assertNotNull(foundPersonSix.getId());
        assertNotNull(foundPersonSix.getFirstName());
        assertNotNull(foundPersonSix.getLastName());
        assertNotNull(foundPersonSix.getAddress());
        assertNotNull(foundPersonSix.getGender());
        assertFalse(foundPersonSix.getEnabled());

        assertEquals(9, foundPersonSix.getId());

        assertEquals("Nelson", foundPersonSix.getFirstName());
        assertEquals("Mvezo", foundPersonSix.getLastName());
        assertEquals("Mvezo – South Africa", foundPersonSix.getAddress());
        assertEquals("Male", foundPersonSix.getGender());
    }

    @Test
    @Order(7)
    public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given().spec(specificationWithoutToken).config(
                        RestAssuredConfig.config()
                                .encoderConfig(
                                        EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
                                )
                )
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .body(person)
                .when()
                .get()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();
    }

    private void mockPerson() {
        person.setFirstName("Nelson");
        person.setLastName("Piquet Souto Maior");
        person.setAddress("Brasília - DF - Brasil");
        person.setGender("Male");
        person.setEnabled(true);
    }

}
