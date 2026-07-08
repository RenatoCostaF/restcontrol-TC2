package com.restcontrol.restcontrol_tc2.infra.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.infra.dto.request.RestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateRestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.RestaurantResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.RestaurantDocument;
import com.restcontrol.restcontrol_tc2.helper.RestaurantHelper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Mapper de infra - Restaurant")
class RestaurantMapperTest {

    private RestaurantMapper restaurantMapper;

    @BeforeEach
    void setUp() {
        restaurantMapper = Mappers.getMapper(RestaurantMapper.class);
    }

    @Test
    @DisplayName("Deve mapear Restaurant para RestaurantDocument")
    void shouldMapRestaurantToDocument() {
        Restaurant restaurant = RestaurantHelper.createRestaurant();

        RestaurantDocument document = restaurantMapper.toDocument(restaurant);

        assertEquals(new ObjectId(restaurant.getId()), document.getId());
        assertEquals(restaurant.getName(), document.getName());
        assertEquals(restaurant.getAddress(), document.getAddress());
        assertEquals(restaurant.getCuisineType(), document.getCuisineType());
        assertEquals(restaurant.getOpeningHours(), document.getOpeningHours());
        assertEquals(restaurant.getOwnerId(), document.getOwnerId());
    }

    @Test
    @DisplayName("Deve mapear Restaurant para RestaurantDocument com ID nulo")
    void shouldMapRestaurantToDocumentWithNullId() {
        Restaurant restaurant = new Restaurant(
                null,
                RestaurantHelper.RESTAURANT_NAME,
                RestaurantHelper.RESTAURANT_ADDRESS,
                RestaurantHelper.RESTAURANT_CUISINE_TYPE,
                RestaurantHelper.RESTAURANT_OPENING_HOURS,
                RestaurantHelper.OWNER_ID
        );

        RestaurantDocument document = restaurantMapper.toDocument(restaurant);

        assertNull(document.getId());
        assertEquals(restaurant.getName(), document.getName());
    }

    @Test
    @DisplayName("Deve mapear RestaurantDocument para Restaurant")
    void shouldMapDocumentToDomain() {
        ObjectId objectId = new ObjectId();
        RestaurantDocument document = new RestaurantDocument();
        document.setId(objectId);
        document.setName(RestaurantHelper.RESTAURANT_NAME);
        document.setAddress(RestaurantHelper.RESTAURANT_ADDRESS);
        document.setCuisineType(RestaurantHelper.RESTAURANT_CUISINE_TYPE);
        document.setOpeningHours(RestaurantHelper.RESTAURANT_OPENING_HOURS);
        document.setOwnerId(RestaurantHelper.OWNER_ID);

        Restaurant restaurant = restaurantMapper.toDomain(document);

        assertEquals(objectId.toHexString(), restaurant.getId());
        assertEquals(document.getName(), restaurant.getName());
        assertEquals(document.getAddress(), restaurant.getAddress());
        assertEquals(document.getCuisineType(), restaurant.getCuisineType());
        assertEquals(document.getOpeningHours(), restaurant.getOpeningHours());
        assertEquals(document.getOwnerId(), restaurant.getOwnerId());
    }

    @Test
    @DisplayName("Deve mapear RestaurantDocument para Restaurant com ID nulo")
    void shouldMapDocumentToDomainWithNullId() {
        RestaurantDocument document = new RestaurantDocument();
        document.setName(RestaurantHelper.RESTAURANT_NAME);
        document.setAddress(RestaurantHelper.RESTAURANT_ADDRESS);
        document.setCuisineType(RestaurantHelper.RESTAURANT_CUISINE_TYPE);
        document.setOpeningHours(RestaurantHelper.RESTAURANT_OPENING_HOURS);
        document.setOwnerId(RestaurantHelper.OWNER_ID);

        Restaurant restaurant = restaurantMapper.toDomain(document);

        assertNull(restaurant.getId());
    }

    @Test
    @DisplayName("Deve mapear RestaurantRequestDTO para CreateRestaurantInputDTO")
    void shouldMapCreateRequestToInput() {
        RestaurantRequestDTO request = new RestaurantRequestDTO(
                RestaurantHelper.RESTAURANT_NAME,
                RestaurantHelper.RESTAURANT_ADDRESS,
                RestaurantHelper.RESTAURANT_CUISINE_TYPE,
                RestaurantHelper.RESTAURANT_OPENING_HOURS,
                RestaurantHelper.OWNER_ID
        );

        CreateRestaurantInputDTO input = restaurantMapper.toCreateRestaurantInput(request);

        assertEquals(request.name(), input.name());
        assertEquals(request.address(), input.address());
        assertEquals(request.cuisineType(), input.cuisineType());
        assertEquals(request.openingHours(), input.openingHours());
        assertEquals(request.ownerId(), input.ownerId());
    }

    @Test
    @DisplayName("Deve mapear UpdateRestaurantRequestDTO para UpdateRestaurantInputDTO")
    void shouldMapUpdateRequestToInput() {
        UpdateRestaurantRequestDTO request = new UpdateRestaurantRequestDTO(
                RestaurantHelper.RESTAURANT_NAME,
                RestaurantHelper.RESTAURANT_ADDRESS,
                RestaurantHelper.RESTAURANT_CUISINE_TYPE,
                RestaurantHelper.RESTAURANT_OPENING_HOURS,
                RestaurantHelper.OWNER_ID
        );

        UpdateRestaurantInputDTO input = restaurantMapper.toUpdateRestaurantInput(request);

        assertEquals(request.name(), input.name());
        assertEquals(request.address(), input.address());
        assertEquals(request.cuisineType(), input.cuisineType());
        assertEquals(request.openingHours(), input.openingHours());
        assertEquals(request.ownerId(), input.ownerId());
    }

    @Test
    @DisplayName("Deve mapear Restaurant para RestaurantDocument com ID em branco")
    void shouldMapRestaurantToDocumentWithBlankId() {
        Restaurant restaurant = new Restaurant(
                "",
                RestaurantHelper.RESTAURANT_NAME,
                RestaurantHelper.RESTAURANT_ADDRESS,
                RestaurantHelper.RESTAURANT_CUISINE_TYPE,
                RestaurantHelper.RESTAURANT_OPENING_HOURS,
                RestaurantHelper.OWNER_ID
        );

        RestaurantDocument document = restaurantMapper.toDocument(restaurant);

        assertNull(document.getId());
        assertEquals(restaurant.getName(), document.getName());
    }

    @Test
    @DisplayName("Deve mapear Restaurant para RestaurantResponseDTO")
    void shouldMapRestaurantToResponseDTO() {
        Restaurant restaurant = RestaurantHelper.createRestaurant();

        RestaurantResponseDTO response = restaurantMapper.toRestaurantResponseDTO(restaurant);

        assertEquals(restaurant.getId(), response.id());
        assertEquals(restaurant.getName(), response.name());
        assertEquals(restaurant.getAddress(), response.address());
        assertEquals(restaurant.getCuisineType(), response.cuisineType());
        assertEquals(restaurant.getOpeningHours(), response.openingHours());
        assertEquals(restaurant.getOwnerId(), response.ownerId());
    }
}
