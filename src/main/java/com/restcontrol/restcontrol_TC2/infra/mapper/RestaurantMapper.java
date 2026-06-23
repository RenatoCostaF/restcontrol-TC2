package com.restcontrol.restcontrol_TC2.infra.mapper;

import com.restcontrol.restcontrol_TC2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.RestaurantDocument;

public class RestaurantMapper {

    public RestaurantDocument toDocument(Restaurant restaurant) {
        RestaurantDocument document = new RestaurantDocument();
        document.setName(restaurant.getName());
        document.setCity(restaurant.getCity());
        document.setZipcode(restaurant.getZipcode());
        document.setStreet(restaurant.getStreet());
        document.setState(restaurant.getState());
        document.setSpecialty(restaurant.getSpecialty());
        document.setOwnerId(restaurant.getOwnerId());

        return document;
    }

    public Restaurant toDomain(RestaurantDocument document) {

        Restaurant restaurant = Restaurant.create(
                document.getName(),
                document.getCity(),
                document.getZipcode(),
                document.getStreet(),
                document.getState(),
                document.getSpecialty(),
                document.getOwnerId()
        );

        return restaurant;
    }
}
