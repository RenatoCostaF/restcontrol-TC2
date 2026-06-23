package com.restcontrol.restcontrol_TC2.infra.mapper;

import com.restcontrol.restcontrol_TC2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_TC2.infra.dto.request.RestaurantRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.request.UpdateRestaurantRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.RestaurantResponseDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.UpdateRestaurantResponseDTO;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.RestaurantDocument;
import com.restcontrol.restcontrol_TC2.domain.adapter.input.CreateRestaurantInput;
import com.restcontrol.restcontrol_TC2.domain.adapter.input.UpdateRestaurantInput;
import org.springframework.stereotype.Component;

@Component("mongoRestaurantMapper")
public class RestaurantMapper {

    public RestaurantDocument toDocument(Restaurant restaurant) {
        RestaurantDocument document = new RestaurantDocument();
        if (restaurant.getId() != null && !restaurant.getId().isBlank()) {
            document.setId(new org.bson.types.ObjectId(restaurant.getId()));
        }
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
        if (document.getId() != null) {
            restaurant.setId(document.getId().toHexString());
        }

        return restaurant;
    }

    public CreateRestaurantInput toCreateRestaurantInput(RestaurantRequestDTO requestDTO) {
        return new CreateRestaurantInput(
                requestDTO.name(),
                requestDTO.city(),
                requestDTO.zipcode(),
                requestDTO.street(),
                requestDTO.state(),
                requestDTO.specialty(),
                requestDTO.ownerId()
        );
    }

    public UpdateRestaurantInput toUpdateRestaurantInput(UpdateRestaurantRequestDTO requestDTO) {
        return new UpdateRestaurantInput(
                requestDTO.name(),
                requestDTO.city(),
                requestDTO.zipcode(),
                requestDTO.street(),
                requestDTO.state(),
                requestDTO.specialty(),
                requestDTO.ownerId()
        );
    }

    public RestaurantResponseDTO toRestaurantResponseDTO(Restaurant restaurant) {
        return new RestaurantResponseDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCity(),
                restaurant.getZipcode(),
                restaurant.getStreet(),
                restaurant.getState(),
                restaurant.getSpecialty(),
                restaurant.getOwnerId()
        );
    }

    public UpdateRestaurantResponseDTO toUpdateRestaurantResponseDTO(Restaurant restaurant) {
        return new UpdateRestaurantResponseDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCity(),
                restaurant.getZipcode(),
                restaurant.getStreet(),
                restaurant.getState(),
                restaurant.getSpecialty(),
                restaurant.getOwnerId()
        );
    }
}
