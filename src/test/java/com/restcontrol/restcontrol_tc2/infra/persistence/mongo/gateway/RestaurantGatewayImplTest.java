package com.restcontrol.restcontrol_tc2.infra.persistence.mongo.gateway;

import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidObjectIdException;
import com.restcontrol.restcontrol_tc2.infra.mapper.RestaurantMapper;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.RestaurantDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.RestaurantRepository;
import com.restcontrol.restcontrol_tc2.helper.RestaurantHelper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantGatewayImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantGatewayImpl restaurantGateway;

    private Restaurant restaurant;
    private RestaurantDocument document;
    private ObjectId objectId;

    @BeforeEach
    void setUp() {
        restaurant = RestaurantHelper.createRestaurant();
        objectId = new ObjectId(RestaurantHelper.RESTAURANT_ID);
        document = new RestaurantDocument();
        document.setId(objectId);
        document.setName(restaurant.getName());
        document.setAddress(restaurant.getAddress());
        document.setCuisineType(restaurant.getCuisineType());
        document.setOpeningHours(restaurant.getOpeningHours());
        document.setOwnerId(restaurant.getOwnerId());
    }

    @Test
    void shouldCreateRestaurant() {
        when(restaurantMapper.toDocument(restaurant)).thenReturn(document);
        when(restaurantRepository.save(document)).thenReturn(document);
        when(restaurantMapper.toDomain(document)).thenReturn(restaurant);

        Restaurant result = restaurantGateway.create(restaurant);

        assertEquals(restaurant, result);
        verify(restaurantMapper).toDocument(restaurant);
        verify(restaurantRepository).save(document);
        verify(restaurantMapper).toDomain(document);
    }

    @Test
    void shouldUpdateRestaurant() {
        when(restaurantMapper.toDocument(restaurant)).thenReturn(document);
        when(restaurantRepository.save(document)).thenReturn(document);
        when(restaurantMapper.toDomain(document)).thenReturn(restaurant);

        Restaurant result = restaurantGateway.update(restaurant);

        assertEquals(restaurant, result);
        verify(restaurantMapper).toDocument(restaurant);
        verify(restaurantRepository).save(document);
        verify(restaurantMapper).toDomain(document);
    }

    @Test
    void shouldListAllRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(List.of(document));
        when(restaurantMapper.toDomain(document)).thenReturn(restaurant);

        List<Restaurant> result = restaurantGateway.listAll();

        assertEquals(1, result.size());
        assertEquals(restaurant, result.getFirst());
    }

    @Test
    void shouldGetRestaurantById() {
        when(restaurantRepository.findById(objectId)).thenReturn(Optional.of(document));
        when(restaurantMapper.toDomain(document)).thenReturn(restaurant);

        Optional<Restaurant> result = restaurantGateway.getById(RestaurantHelper.RESTAURANT_ID);

        assertTrue(result.isPresent());
        assertEquals(restaurant, result.get());
    }

    @Test
    void shouldReturnEmptyWhenRestaurantNotFound() {
        when(restaurantRepository.findById(objectId)).thenReturn(Optional.empty());

        Optional<Restaurant> result = restaurantGateway.getById(RestaurantHelper.RESTAURANT_ID);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldGetRestaurantByName() {
        when(restaurantRepository.findByName(restaurant.getName())).thenReturn(List.of(document));
        when(restaurantMapper.toDomain(document)).thenReturn(restaurant);

        Optional<Restaurant> result = restaurantGateway.getByName(restaurant.getName());

        assertTrue(result.isPresent());
        assertEquals(restaurant, result.get());
    }

    @Test
    void shouldReturnEmptyWhenRestaurantNameNotFound() {
        when(restaurantRepository.findByName("Unknown")).thenReturn(List.of());

        Optional<Restaurant> result = restaurantGateway.getByName("Unknown");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldDeleteRestaurant() {
        restaurantGateway.delete(RestaurantHelper.RESTAURANT_ID);

        verify(restaurantRepository).deleteById(objectId);
    }

    @Test
    void shouldThrowWhenGetByIdReceivesInvalidId() {
        assertThrows(InvalidObjectIdException.class, () -> restaurantGateway.getById("invalid-id"));
        assertThrows(InvalidObjectIdException.class, () -> restaurantGateway.getById(null));
        assertThrows(InvalidObjectIdException.class, () -> restaurantGateway.getById(""));
    }

    @Test
    void shouldThrowWhenDeleteReceivesInvalidId() {
        assertThrows(InvalidObjectIdException.class, () -> restaurantGateway.delete(null));
        assertThrows(InvalidObjectIdException.class, () -> restaurantGateway.delete(""));
        assertThrows(InvalidObjectIdException.class, () -> restaurantGateway.delete("invalid-id"));
    }
}