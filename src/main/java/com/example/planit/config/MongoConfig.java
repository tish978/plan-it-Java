package com.example.planit.config;

import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.util.Assert;

@Configuration
public class MongoConfig {

    private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Bean
    public SimpleMongoClientDatabaseFactory mongoDatabaseFactory() {
        logger.info("Mongo URI: {}", mongoUri);
        logger.info("Database Name: {}", databaseName);

        Assert.hasText(databaseName, "Database name must not be empty!");
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), databaseName);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDatabaseFactory());
    }

    @Bean
    public void logProperties() {
        logger.info("Logging properties from MongoConfig");
        logger.info("Mongo URI: {}", mongoUri);
        logger.info("Database Name: {}", databaseName);
    }
}
