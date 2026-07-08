package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.helper.RestaurantHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.restcontrol.restcontrol_tc2.domain.gateway.RestaurantGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.domain.exception.ActionNotAllowedForRunningUser;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidRestaurantOwnerTypeException;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantDuplicateIdentified;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private UserGateway userGateway;

    @Mock
    private UserTypeGateway userTypeGateway;

    private UserType userType;

    private RestaurantUseCaseImpl restaurantUseCase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        restaurantUseCase = new RestaurantUseCaseImpl(restaurantGateway, userGateway, userTypeGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void createWithValidOwnerTest(){
        //Arrange
        var userType = RestaurantHelper.createUserType();
        var user = RestaurantHelper.createRestaurantOwner();
        var restaurant = RestaurantHelper.createRestaurant();
        when(restaurantGateway.create(any(Restaurant.class))).thenReturn(restaurant);
        when(userGateway.getById(user.getId())).thenReturn(Optional.of(user));
        when(userTypeGateway.getById(userType.getId())).thenReturn(Optional.of(userType));

        //Act
        var createdRestaurant = restaurantUseCase.create(restaurant);

        //Assert
        assertNotNull(createdRestaurant);
        assertEquals(restaurant.getName(), createdRestaurant.getName());
        verify(restaurantGateway, times(1)).create(restaurant);
    }

    @Test
    void createWithOwnerNotFoundTest() {
        // Arrange
        var restaurant = RestaurantHelper.createRestaurant();
        when(userGateway.getById(restaurant.getOwnerId())).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(UserNotFoundException.class, () -> restaurantUseCase.create(restaurant));
        verify(restaurantGateway, never()).create(any(Restaurant.class));
    }

    @Test
    void createWithUserTypeNotFoundTest() {
        // Arrange
        var user = RestaurantHelper.createRestaurantOwner();
        var restaurant = RestaurantHelper.createRestaurant();
        when(userGateway.getById(restaurant.getOwnerId())).thenReturn(Optional.of(user));
        when(userTypeGateway.getById(user.getUserTypeId())).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(UserTypeNotFoundException.class, () -> restaurantUseCase.create(restaurant));
        verify(restaurantGateway, never()).create(any(Restaurant.class));
    }

    @Test
    void createWithInvalidOwnerTypeTest() {
        // Arrange
        var user = RestaurantHelper.createRestaurantOwner();
        var customerType = RestaurantHelper.createCustomerType();
        var restaurant = RestaurantHelper.createRestaurant();
        when(userGateway.getById(restaurant.getOwnerId())).thenReturn(Optional.of(user));
        when(userTypeGateway.getById(user.getUserTypeId())).thenReturn(Optional.of(customerType));

        // Act + Assert
        assertThrows(InvalidRestaurantOwnerTypeException.class, () -> restaurantUseCase.create(restaurant));
        verify(restaurantGateway, never()).create(any(Restaurant.class));
    }

    @Test
    void updateValidRestaurantTest(){
        // Arrange
        var userType = RestaurantHelper.createUserType();
        var user = RestaurantHelper.createRestaurantOwner();
        var restaurant = RestaurantHelper.createRestaurant();
        var updatedRestaurant = new Restaurant(
                RestaurantHelper.RESTAURANT_ID,
                "New Name",
                "New Address, 999",
                "Japanese",
                "from 5PM - 11PM",
                RestaurantHelper.OWNER_ID
        );
        when(restaurantGateway.getById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(userGateway.getById(user.getId())).thenReturn(Optional.of(user));
        when(userTypeGateway.getById(userType.getId())).thenReturn(Optional.of(userType));
        when(restaurantGateway.update(any(Restaurant.class))).thenReturn(updatedRestaurant);

        // Act
        var result = restaurantUseCase.update(updatedRestaurant);

        // Assert
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(restaurantGateway, times(1)).update(updatedRestaurant);
    }

    @Test
    void updateInvalidRestaurant(){
        // Arrange
        var restaurant = RestaurantHelper.createRestaurant();
        when(restaurantGateway.getById(restaurant.getId())).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RestaurantNotFoundException.class, () -> restaurantUseCase.update(restaurant));
        verify(restaurantGateway, never()).update(any(Restaurant.class));
    }

    @Test
    void updateWithOwnerNotFoundTest() {
        var restaurant = RestaurantHelper.createRestaurant();
        when(restaurantGateway.getById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(userGateway.getById(restaurant.getOwnerId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> restaurantUseCase.update(restaurant));
        verify(restaurantGateway, never()).update(any(Restaurant.class));
    }

    @Test
    void updateWithUserTypeNotFoundTest() {
        var user = RestaurantHelper.createRestaurantOwner();
        var restaurant = RestaurantHelper.createRestaurant();
        when(restaurantGateway.getById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(userGateway.getById(restaurant.getOwnerId())).thenReturn(Optional.of(user));
        when(userTypeGateway.getById(user.getUserTypeId())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> restaurantUseCase.update(restaurant));
        verify(restaurantGateway, never()).update(any(Restaurant.class));
    }

    @Test
    void updateWithInvalidOwnerTypeTest() {
        var user = RestaurantHelper.createRestaurantOwner();
        var customerType = RestaurantHelper.createCustomerType();
        var restaurant = RestaurantHelper.createRestaurant();
        when(restaurantGateway.getById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(userGateway.getById(restaurant.getOwnerId())).thenReturn(Optional.of(user));
        when(userTypeGateway.getById(user.getUserTypeId())).thenReturn(Optional.of(customerType));

        assertThrows(InvalidRestaurantOwnerTypeException.class, () -> restaurantUseCase.update(restaurant));
        verify(restaurantGateway, never()).update(any(Restaurant.class));
    }

    @Test
    void listAllTest(){
        // Arrange
        var restaurant = RestaurantHelper.createRestaurant();
        when(restaurantGateway.listAll()).thenReturn(List.of(restaurant));

        //Act
        List<Restaurant> restaurants = restaurantUseCase.listAll();

        //Assert
        assertEquals(1, restaurants.size());
        assertEquals(restaurant.getName(), restaurants.get(0).getName());
        verify(restaurantGateway, times(1)).listAll();
    }

    @Test
    void getByNameValidTest(){
        //Arrange
        var restaurant = RestaurantHelper.createRestaurant();
        String name = restaurant.getName();
        when(restaurantGateway.getByName(name)).thenReturn(Optional.of(restaurant));

        //Act
        Restaurant result = restaurantUseCase.getByName(name);

        //Assert
        assertEquals(name, result.getName());
        verify(restaurantGateway, times(1)).getByName(name);
    }

    @Test
    void getByNameInvalidTest(){
        // Arrange
        String name = "Rest Test";
        when(restaurantGateway.getByName(name)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RestaurantNotFoundException.class, () -> restaurantUseCase.getByName(name));
        verify(restaurantGateway, times(1)).getByName(name);
    }

    @Test
    void getByIdTest(){
        // Arrange
        var restaurant = RestaurantHelper.createRestaurant();
        when(restaurantGateway.getById(restaurant.getId())).thenReturn(Optional.of(restaurant));

        //Act
        Restaurant result = restaurantUseCase.getById(RestaurantHelper.RESTAURANT_ID);

        //Assert
        assertEquals(restaurant.getName(), result.getName());
        verify(restaurantGateway, times(1)).getById(RestaurantHelper.RESTAURANT_ID);
    }

    @Test
    void deleteTest(){
        // Arrange
        /*var userType = RestaurantHelper.createUserType();*/
        var user = RestaurantHelper.createRestaurantOwner();
        var restaurant = RestaurantHelper.createRestaurant();
        var updatedRestaurant = new Restaurant(
                RestaurantHelper.RESTAURANT_ID,
                "New Name",
                "New Address, 999",
                "Japanese",
                "from 5PM - 11PM",
                RestaurantHelper.OWNER_ID
        );
        when(restaurantGateway.getById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(userGateway.getById(user.getId())).thenReturn(Optional.of(user));
        /*when(userTypeGateway.getById(userType.getId())).thenReturn(Optional.of(userType));*/
        /*when(restaurantGateway.update(any(Restaurant.class))).thenReturn(updatedRestaurant);*/

        //Act
        restaurantUseCase.delete(RestaurantHelper.RESTAURANT_ID, RestaurantHelper.OWNER_ID);

        //Assert
        verify(restaurantGateway, times(1)).delete(RestaurantHelper.RESTAURANT_ID);
    }

    @Test
    void getByIdInvalidTest() {
        // Arrange
        when(restaurantGateway.getById(RestaurantHelper.RESTAURANT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> restaurantUseCase.getById(RestaurantHelper.RESTAURANT_ID));
        verify(restaurantGateway, times(1)).getById(RestaurantHelper.RESTAURANT_ID);
    }

    @Test
    void deleteRestaurantNotFoundTest() {
        // Arrange
        when(restaurantGateway.getById(RestaurantHelper.RESTAURANT_ID)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RestaurantNotFoundException.class, () -> restaurantUseCase.delete(RestaurantHelper.RESTAURANT_ID, RestaurantHelper.OWNER_ID));
        verify(restaurantGateway, never()).delete(any());
    }

    @Test
    void deleteUserNotFoundTest() {
        // Arrange
        var restaurant = RestaurantHelper.createRestaurant();
        when(restaurantGateway.getById(RestaurantHelper.RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(userGateway.getById(RestaurantHelper.OWNER_ID)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(UserNotFoundException.class, () -> restaurantUseCase.delete(RestaurantHelper.RESTAURANT_ID, RestaurantHelper.OWNER_ID));
        verify(restaurantGateway, never()).delete(any());
    }

    @Test
    void deleteWithUnauthorizedUserTest() {
        var restaurant = RestaurantHelper.createRestaurant();
        var runningUser = new User(
                "other-user-id",
                "Other User",
                "other@example.com",
                "password1!",
                RestaurantHelper.USERTYPE_ID
        );
        when(restaurantGateway.getById(RestaurantHelper.RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(userGateway.getById("other-user-id")).thenReturn(Optional.of(runningUser));

        assertThrows(ActionNotAllowedForRunningUser.class,
                () -> restaurantUseCase.delete(RestaurantHelper.RESTAURANT_ID, "other-user-id"));
        verify(restaurantGateway, never()).delete(any());
    }

    @Test
    void shouldThrowRestaurantDuplicateIdentifiedWithMessage() {
        var message = "Restaurant already exists";
        var exception = new RestaurantDuplicateIdentified(message);
        assertEquals(message, exception.getMessage());
    }
}
