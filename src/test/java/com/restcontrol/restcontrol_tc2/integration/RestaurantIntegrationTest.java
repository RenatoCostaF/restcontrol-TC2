package com.restcontrol.restcontrol_tc2.integration;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.RestaurantDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.RestaurantRepository;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.UserRepository;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.UserTypeRepository;
import org.bson.types.ObjectId;
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
        userTypeId = userTypeRepository.findByCode(UserType.RESTAURANT_OWNER_CODE)
                .orElseThrow()
                .getId();

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
        mockMvc.perform(get("/v1/restaurants/" + new ObjectId().toHexString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void mustReturn404WhenGettingRestaurantByUnknownName() throws Exception {
        mockMvc.perform(get("/v1/restaurants").param("name", "Unknown Restaurant"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Restaurant not found"));
    }

    @Test
    void mustReturn404WhenCreatingRestaurantWithMissingOwner() throws Exception {
        var body = """
                {
                    "name": "New Restaurant",
                    "address": "456 Oak Ave",
                    "cuisineType": "Japanese",
                    "openingHours": "10:00-23:00",
                    "ownerId": "%s"
                }
                """.formatted(new ObjectId().toHexString());

        mockMvc.perform(post("/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("User not found"));
    }

    @Test
    void mustReturn400WhenCreatingRestaurantWithInvalidOwnerType() throws Exception {
        var customerTypeId = userTypeRepository.findByCode(UserType.CUSTOMER_CODE)
                .orElseThrow()
                .getId();

        UserDocument customer = new UserDocument();
        customer.setId(new ObjectId());
        customer.setName("Customer User");
        customer.setEmail("customer@email.com");
        customer.setPassword("password");
        customer.setUserTypeId(customerTypeId.toHexString());
        userRepository.save(customer);

        var body = """
                {
                    "name": "New Restaurant",
                    "address": "456 Oak Ave",
                    "cuisineType": "Japanese",
                    "openingHours": "10:00-23:00",
                    "ownerId": "%s"
                }
                """.formatted(customer.getId().toHexString());

        mockMvc.perform(post("/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Only users with type code 'RESTAURANT_OWNER' can own restaurants"));
    }

    @Test
    void mustReturn400WhenUpdatePayloadIsInvalid() throws Exception {
        var body = """
                {
                    "name": "",
                    "address": "",
                    "cuisineType": "",
                    "openingHours": "",
                    "ownerId": ""
                }
                """;

        mockMvc.perform(put("/v1/restaurants/" + restaurantId.toHexString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"));
    }

    @Test
    void mustReturn403WhenDeletingRestaurantAsNonOwner() throws Exception {
        var customerTypeId = userTypeRepository.findByCode(UserType.CUSTOMER_CODE)
                .orElseThrow()
                .getId();

        UserDocument customer = new UserDocument();
        customer.setId(new ObjectId());
        customer.setName("Customer User");
        customer.setEmail("customer@email.com");
        customer.setPassword("password");
        customer.setUserTypeId(customerTypeId.toHexString());
        userRepository.save(customer);

        mockMvc.perform(delete("/v1/restaurants/" + restaurantId.toHexString())
                        .param("runningUserId", customer.getId().toHexString()))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.detail").value("Only the restaurant owner can delete the restaurant!"));
    }
}
