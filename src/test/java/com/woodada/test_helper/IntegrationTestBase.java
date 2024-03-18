package com.woodada.test_helper;

import com.woodada.test_helper.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public abstract class IntegrationTestBase {

    @Autowired
    private RDBInitialization rdbInitialization;

    @Autowired
    private RedisInitialization redisInitialization;

    @AfterEach
    void afterEach() {
        rdbInitialization.truncateAllEntity();
        redisInitialization.initRedis();
    }
}
