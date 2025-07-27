package com.fiap.estoque.controller;

import com.fiap.estoque.controller.json.StockDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StockControllerTest {

    static UUID testProductId;

    @BeforeAll
    void setUp() {
        testProductId = UUID.randomUUID();
    }

    @Test
    @Order(1)
    void testCreateStock() {
        StockDTO dto = new StockDTO();
        dto.setProductId(testProductId);
        dto.setQuantity(100);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/stocks")
                .then()
                .statusCode(201)
                .body("productId", Matchers.equalTo(testProductId.toString()))
                .body("quantity", Matchers.equalTo(100));
    }

    @Test
    @Order(2)
    void testCreateDuplicatedStock() {
        StockDTO dto = new StockDTO();
        dto.setProductId(testProductId);
        dto.setQuantity(50);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/stocks")
                .then()
                .statusCode(409);
    }

    @Test
    @Order(3)
    void testGetAllStock() {
        when()
                .get("/stocks")
                .then()
                .statusCode(200)
                .body("size()", Matchers.greaterThan(0));
    }

    @Test
    @Order(4)
    void testGetStockByProductId() {
        given()
                .pathParam("productId", testProductId)
                .when()
                .get("/stocks/products/{productId}")
                .then()
                .statusCode(200)
                .body("productId", Matchers.equalTo(testProductId.toString()));
    }

    @Test
    @Order(5)
    void testUpdateStock() {
        StockDTO update = new StockDTO();
        update.setQuantity(200);

        given()
                .contentType(ContentType.JSON)
                .body(update)
                .pathParam("productId", testProductId)
                .when()
                .put("/stocks/products/{productId}")
                .then()
                .statusCode(200)
                .body("quantity", Matchers.equalTo(200));
    }

    @Test
    @Order(6)
    void testDeleteStock() {
        given()
                .pathParam("productId", testProductId)
                .when()
                .delete("/stocks/products/{productId}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(7)
    void testGetStockNotFound() {
        given()
                .pathParam("productId", UUID.randomUUID())
                .when()
                .get("/stocks/products/{productId}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(8)
    void testUpdateStockNotFound() {
        StockDTO update = new StockDTO();
        update.setQuantity(150);

        given()
                .contentType(ContentType.JSON)
                .body(update)
                .pathParam("productId", UUID.randomUUID())
                .when()
                .put("/stocks/products/{productId}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(9)
    void testCreateStockWithInvalidQuantity() {
        StockDTO dto = new StockDTO();
        dto.setProductId(UUID.randomUUID());
        dto.setQuantity(-1);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/stocks")
                .then()
                .statusCode(400);
    }
}