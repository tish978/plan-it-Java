package com.example.planit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class PlanItApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(PlanItApplicationTests.class);

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Test
    void contextLoads() {
        logger.info("Mongo URI: {}", mongoUri);
        logger.info("Database Name: {}", databaseName);
    }
}
