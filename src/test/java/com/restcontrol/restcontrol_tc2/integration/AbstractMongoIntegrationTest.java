package com.restcontrol.restcontrol_tc2.integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
@Testcontainers(disabledWithoutDocker = true)
public abstract class AbstractMongoIntegrationTest {

    @Container
    private static final MongoDBContainer MONGO_DB_CONTAINER =
            new MongoDBContainer(DockerImageName.parse("mongo:8.0"));

    @DynamicPropertySource
    static void configureMongo(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getReplicaSetUrl);
    }

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void cleanDatabase() {
        mongoTemplate.dropCollection("users");
        mongoTemplate.dropCollection("restaurants");
        mongoTemplate.dropCollection("menu_items");
    }
}
