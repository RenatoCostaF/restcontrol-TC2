package com.restcontrol.restcontrol_tc2.integration;

import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.RestaurantDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserTypeDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.RestaurantRepository;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.UserRepository;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.UserTypeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RestaurantIntegrationTest extends AbstractMongoIntegrationTest {

    @Autowired RestaurantRepository restaurantRepository;
    @Autowired UserRepository userRepository;
    @Autowired UserTypeRepository userTypeRepository;

    private ObjectId userTypeId;
    private ObjectId userId;
    private ObjectId restaurantId;

    @BeforeEach
    void setUp() {
        UserTypeDocument userType = new UserTypeDocument();
        userType.setId(new ObjectId());
        userType.setName("Restaurant Owner");
        userType.setCode("RESTAURANT_OWNER");
        userTypeRepository.save(userType);
        userTypeId = userType.getId();

        UserDocument user = new UserDocument();
        user.setId(new ObjectId());
        user.setName("Owner Name");
        user.setEmail("owner@email.com");
        user.setPassword("password");
        user.setUserTypeId(userTypeId.toHexString());
        userRepository.save(user);
        userId = user.getId();

        RestaurantDocument restaurant = new RestaurantDocument();
        restaurant.setId(new ObjectId());
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Main St");
        restaurant.setCuisineType("Italian");
        restaurant.setOpeningHours("08:00-22:00");
        restaurant.setOwnerId(userId.toHexString());
        restaurantRepository.save(restaurant);
        restaurantId = restaurant.getId();
    }

    @AfterEach
    void tearDown() {
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
        userTypeRepository.deleteAll();
    }

    @Test
    void mustAllowCreateRestaurant() throws Exception {
        var body = """
                {
                    "name": "New Restaurant",
                    "address": "456 Oak Ave",
                    "cuisineType": "Japanese",
                    "openingHours": "10:00-23:00",
                    "ownerId": "%s"
                }
                """.formatted(userId.toHexString());

        mockMvc.perform(post("/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Restaurant"))
                .andExpect(jsonPath("$.ownerId").value(userId.toHexString()));
    }

    @Test
    void mustAllowListAllRestaurants() throws Exception {
        mockMvc.perform(get("/v1/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Restaurant"));
    }

    @Test
    void mustAllowGetRestaurantByName() throws Exception {
        mockMvc.perform(get("/v1/restaurants")
                        .param("name", "Test Restaurant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Restaurant"));
    }

    @Test
    void mustAllowGetRestaurantById() throws Exception {
        mockMvc.perform(get("/v1/restaurants/" + restaurantId.toHexString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurantId.toHexString()))
                .andExpect(jsonPath("$.name").value("Test Restaurant"));
    }

    @Test
    void mustAllowUpdateRestaurant() throws Exception {
        var body = """
                {
                    "name": "Updated Restaurant",
                    "address": "789 Pine Rd",
                    "cuisineType": "French",
                    "openingHours": "11:00-21:00",
                    "ownerId": "%s"
                }
                """.formatted(userId.toHexString());

        mockMvc.perform(put("/v1/restaurants/" + restaurantId.toHexString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Restaurant"));
    }

    @Test
    void mustAllowDeleteRestaurant() throws Exception {
        mockMvc.perform(delete("/v1/restaurants/" + restaurantId.toHexString())
                        .param("runningUserId", userId.toHexString()))
                .andExpect(status().isOk());
    }

    @Test
    void mustReturn404WhenRestaurantNotFound() throws Exception {
        var unknownId = new ObjectId().toHexString();

        mockMvc.perform(get("/v1/restaurants/" + unknownId))
                .andExpect(status().isNotFound());
    }
}
