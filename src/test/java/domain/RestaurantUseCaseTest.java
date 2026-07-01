package domain;

import helper.RestaurantHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.restcontrol.restcontrol_tc2.domain.gateway.RestaurantGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_tc2.domain.usecase.impl.RestaurantUseCaseImpl;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidRestaurantOwnerTypeException;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestaurantUseCaseTest {

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


    /*
    * Restaurant create(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

    List<Restaurant> listAll();

    Restaurant getByName(String name);

    Restaurant getById(String id);

    void delete(String id, String userId);
    * */
}
