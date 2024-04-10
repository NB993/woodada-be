package com.woodada.test_helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodada.common.config.jpa.AuditorAwareImpl;
import com.woodada.test_helper.annotation.IntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.AuditorAware;

@IntegrationTest
public abstract class AcceptanceTestBase {

    @LocalServerPort private int port;

    @Autowired private RDBInitialization rdbInitialization;
    @Autowired private RedisInitialization redisInitialization;
    @Autowired private AuditorAware<Long> auditorAware;

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
        ((AuditorAwareImpl) auditorAware).setCurrentAuditor(0L); // todo 다이어리 CRUD 끝나고 변경..
    }
}
