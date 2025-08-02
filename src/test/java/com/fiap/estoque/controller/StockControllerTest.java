package com.fiap.estoque.controller;

import com.fiap.estoque.controller.json.StockDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    @Test
    @Order(10)
    void testBatchStockDeduction() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();

        StockDTO stock1 = new StockDTO();
        stock1.setProductId(productId1);
        stock1.setQuantity(50);

        given()
                .contentType(ContentType.JSON)
                .body(stock1)
                .when()
                .post("/stocks")
                .then()
                .statusCode(201);

        StockDTO stock2 = new StockDTO();
        stock2.setProductId(productId2);
        stock2.setQuantity(30);

        given()
                .contentType(ContentType.JSON)
                .body(stock2)
                .when()
                .post("/stocks")
                .then()
                .statusCode(201);

        StockDTO deduction1 = new StockDTO();
        deduction1.setProductId(productId1);
        deduction1.setQuantity(10);

        StockDTO deduction2 = new StockDTO();
        deduction2.setProductId(productId2);
        deduction2.setQuantity(5);

        List<StockDTO> deductions = Arrays.asList(deduction1, deduction2);

        given()
                .contentType(ContentType.JSON)
                .body(deductions)
                .when()
                .post("/stocks/deduct")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].quantity", Matchers.equalTo(40))
                .body("[1].quantity", Matchers.equalTo(25));
    }

    @Test
    @Order(11)
    void testBatchStockDeductionInsufficientStock() {
        UUID productId = UUID.randomUUID();

        StockDTO stock = new StockDTO();
        stock.setProductId(productId);
        stock.setQuantity(5);

        given()
                .contentType(ContentType.JSON)
                .body(stock)
                .when()
                .post("/stocks")
                .then()
                .statusCode(201);

        StockDTO deduction = new StockDTO();
        deduction.setProductId(productId);
        deduction.setQuantity(10);

        List<StockDTO> deductions = Collections.singletonList(deduction);

        given()
                .contentType(ContentType.JSON)
                .body(deductions)
                .when()
                .post("/stocks/deduct")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(12)
    void testBatchStockReversal() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();

        StockDTO stock1 = new StockDTO();
        stock1.setProductId(productId1);
        stock1.setQuantity(20);

        given()
                .contentType(ContentType.JSON)
                .body(stock1)
                .when()
                .post("/stocks")
                .then()
                .statusCode(201);

        StockDTO stock2 = new StockDTO();
        stock2.setProductId(productId2);
        stock2.setQuantity(15);

        given()
                .contentType(ContentType.JSON)
                .body(stock2)
                .when()
                .post("/stocks")
                .then()
                .statusCode(201);

        StockDTO reversal1 = new StockDTO();
        reversal1.setProductId(productId1);
        reversal1.setQuantity(10);

        StockDTO reversal2 = new StockDTO();
        reversal2.setProductId(productId2);
        reversal2.setQuantity(5);

        List<StockDTO> reversals = Arrays.asList(reversal1, reversal2);

        given()
                .contentType(ContentType.JSON)
                .body(reversals)
                .when()
                .post("/stocks/reverse")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].quantity", Matchers.equalTo(30))
                .body("[1].quantity", Matchers.equalTo(20));
    }

    @Test
    @Order(13)
    void testBatchStockReversalWithInvalidProductId() {
        StockDTO reversal = new StockDTO();
        reversal.setProductId(UUID.randomUUID());
        reversal.setQuantity(10);

        List<StockDTO> reversals = Collections.singletonList(reversal);

        given()
                .contentType(ContentType.JSON)
                .body(reversals)
                .when()
                .post("/stocks/reverse")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(14)
    void testBatchStockDeductionWithInvalidProductId() {
        StockDTO deduction = new StockDTO();
        deduction.setProductId(UUID.randomUUID());
        deduction.setQuantity(10);

        List<StockDTO> deductions = Collections.singletonList(deduction);

        given()
                .contentType(ContentType.JSON)
                .body(deductions)
                .when()
                .post("/stocks/deduct")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(15)
    void testBatchStockDeductionWithEmptyList() {
        List<StockDTO> emptyDeductions = Collections.emptyList();

        given()
                .contentType(ContentType.JSON)
                .body(emptyDeductions)
                .when()
                .post("/stocks/deduct")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(0));
    }

    @Test
    @Order(16)
    void testBatchStockReversalWithEmptyList() {
        List<StockDTO> emptyReversals = Collections.emptyList();

        given()
                .contentType(ContentType.JSON)
                .body(emptyReversals)
                .when()
                .post("/stocks/reverse")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(0));
    }

    @Test
    @Order(17)
    void testBatchStockDeductionWithNegativeQuantity() {
        UUID productId = UUID.randomUUID();

        StockDTO stock = new StockDTO();
        stock.setProductId(productId);
        stock.setQuantity(50);

        given()
                .contentType(ContentType.JSON)
                .body(stock)
                .when()
                .post("/stocks")
                .then()
                .statusCode(201);

        StockDTO deduction = new StockDTO();
        deduction.setProductId(productId);
        deduction.setQuantity(-5);

        List<StockDTO> deductions = Collections.singletonList(deduction);

        given()
                .contentType(ContentType.JSON)
                .body(deductions)
                .when()
                .post("/stocks/deduct")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(18)
    void testBatchStockReversalWithNegativeQuantity() {
        UUID productId = UUID.randomUUID();

        StockDTO stock = new StockDTO();
        stock.setProductId(productId);
        stock.setQuantity(20);

        given()
                .contentType(ContentType.JSON)
                .body(stock)
                .when()
                .post("/stocks")
                .then()
                .statusCode(201);

        StockDTO reversal = new StockDTO();
        reversal.setProductId(productId);
        reversal.setQuantity(-3);

        List<StockDTO> reversals = Collections.singletonList(reversal);

        given()
                .contentType(ContentType.JSON)
                .body(reversals)
                .when()
                .post("/stocks/reverse")
                .then()
                .statusCode(400);
    }
}