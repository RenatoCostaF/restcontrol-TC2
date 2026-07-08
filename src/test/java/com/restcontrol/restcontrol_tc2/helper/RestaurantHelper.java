package com.restcontrol.restcontrol_tc2.helper;

import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.dto.CreateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateRestaurantInputDTO;

public class RestaurantHelper {

    public static final String RESTAURANT_ID = "507f1f77bcf86cd799439011";
    public static final String OWNER_ID = "db5e4d27-2c97-4b71-9df7-7041f6f1c713";
    public static final String USERTYPE_ID = "123e4567-e89b-12d3-a456-426614174000";
    public static final String CUSTOMER_USERTYPE_ID = "223e4567-e89b-12d3-a456-426614174001";
    public static final String RESTAURANT_NAME = "AWESOME RESTAURANT";
    public static final String RESTAURANT_CUISINE_TYPE = "Italian";
    public static final String RESTAURANT_ADDRESS = "Random Street, 737";
    public static final String RESTAURANT_OPENING_HOURS = "From 6PM - 12AM";
    public static Restaurant createRestaurant(){
        return new Restaurant(
                RESTAURANT_ID,
                "Rest Test",
                "Awesome Street, 1001",
                "Italian",
                "from 6PM - 1AM",
                OWNER_ID
        );
    }

    public static User createRestaurantOwner(){
        return new User(
                OWNER_ID,
                "John Owner",
                "john@owner.com",
                "john1234!",
                USERTYPE_ID
        );
    }

    public static UserType createUserType() {
        return new UserType(
                USERTYPE_ID,
                "OWNER TYPE",
                UserType.RESTAURANT_OWNER_CODE
        );
    }

    public static UserType createCustomerType() {
        return new UserType(
                CUSTOMER_USERTYPE_ID,
                "Customer Type",
                UserType.CUSTOMER_CODE
        );
    }

    public static CreateRestaurantInputDTO createRestaurantInputDTO(){
        return new CreateRestaurantInputDTO(
                null,
                RESTAURANT_NAME,
                RESTAURANT_ADDRESS,
                RESTAURANT_CUISINE_TYPE,
                RESTAURANT_OPENING_HOURS,
                OWNER_ID
        );
    }

    public static UpdateRestaurantInputDTO updateRestaurantInputDTO(){
        return new UpdateRestaurantInputDTO(
                "New Restaurant Name",
                "Changed Street",
                "Arabic",
                "From 10AM - 14PM",
                OWNER_ID
        );
    }
}
