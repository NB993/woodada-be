package com.woodada.test_helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodada.test_helper.annotation.IntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

@IntegrationTest
public abstract class AcceptanceTestBase {

    @LocalServerPort private int port;

    @Autowired private RDBInitialization rdbInitialization;
    @Autowired private RedisInitialization redisInitialization;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @AfterEach
    void cleanUp() {
        rdbInitialization.truncateAllEntity();
        redisInitialization.initRedis();
    }
}
