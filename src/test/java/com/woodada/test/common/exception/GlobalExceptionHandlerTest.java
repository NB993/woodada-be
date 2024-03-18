package com.woodada.test.common.exception;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import com.woodada.test.common.exception.ExceptionTestController.TestRequestBody;
import com.woodada.test_helper.AcceptanceTestBase;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("[integration test] GlobalExceptionHandler 통합 테스트")
public class GlobalExceptionHandlerTest extends AcceptanceTestBase {

    @DisplayName("IllegalArgumentException 핸들러 테스트")
    @Test
    void illegal_argument_exception_ㄲresponse() {
        when()
            .post("/exception/illegal-argument-exception")
        .then()
            .statusCode(400)
            .body("success", equalTo(false))
            .body("error.code", equalTo("400"))
            .body("error.message", notNullValue())
            .body("error.validations", empty())
            .log().all();
    }
//
    @DisplayName("HttpRequestMethodNotSupportedException 핸들러 테스트")
    @Test
    void http_request_method_not_supported_exception_response() {
        when()
            .delete("/exception/http-request-method-not-supported-exception")
        .then()
            .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
            .body("success", equalTo(false))
            .body("error.code", equalTo("405"))
            .body("error.message", notNullValue())
            .body("error.validations", empty())
            .log().all();
    }

    @DisplayName("HttpMessageNotReadableException 핸들러 테스트")
    @Test
    void http_message_not_readable_exception_response() {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .post("/exception/http-message-not-readable-exception")
            //body 입력 X
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("success", equalTo(false))
            .body("error.code", equalTo("400"))
            .body("error.message", notNullValue())
            .body("error.validations", empty())
            .log().all();
    }

    @DisplayName("MethodArgumentNotValidException 핸들러 테스트")
    @Test
    void method_argument_not_valid_exception_response() {
        final TestRequestBody invalidBody = new TestRequestBody("1234", -1, List.of());

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(invalidBody)
        .when()
            .post("/exception/http-message-not-readable-exception")
            //body 입력 X
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("success", equalTo(false))
            .body("error.code", equalTo("400"))
            .body("error.message", equalTo("입력 조건을 위반하였습니다."))
            .body("error.validations", hasSize(greaterThan(0)))
            .log().all();
    }

    @DisplayName("WddException 핸들러 테스트")
    @Test
    void wdd_exception_response() {
        when()
            .get("/exception/business-exception")
            //body 입력 X
        .then()
            .statusCode(TestException.TEST.getStatus().value())
            .body("success", equalTo(false))
            .body("error.code", equalTo(TestException.TEST.getCode()))
            .body("error.message", equalTo(TestException.TEST.getMessage()))
            .body("error.validations", empty())
            .log().all();
    }

    @DisplayName("Exception 핸들러 테스트")
    @Test
    void server_error_response() {
        when()
            .get("/exception/internal-server-error")
        .then()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body("success", equalTo(false))
            .body("error.code", equalTo("500"))
            .body("error.message", equalTo("서버 에러"))
            .body("error.validations", empty())
            .log().all();
    }
}
