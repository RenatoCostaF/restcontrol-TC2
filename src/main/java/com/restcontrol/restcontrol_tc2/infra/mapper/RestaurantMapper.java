package com.restcontrol.restcontrol_tc2.infra.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.infra.dto.request.RestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateRestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.RestaurantResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UpdateRestaurantResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.RestaurantDocument;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class RestaurantMapper {

    public abstract RestaurantDocument toDocument(Restaurant restaurant);

    public abstract Restaurant toDomain(RestaurantDocument document);

    public abstract CreateRestaurantInputDTO toCreateRestaurantInput(RestaurantRequestDTO restaurantRequestDTO);

    public abstract RestaurantResponseDTO toRestaurantResponseDTO(Restaurant restaurant);

    public abstract UpdateRestaurantInputDTO toUpdateRestaurantInput(UpdateRestaurantRequestDTO updateRestaurantRequestDTO);

    public abstract UpdateRestaurantResponseDTO toUpdateRestaurantResponseDTO(Restaurant restaurant);

    protected ObjectId map(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }

        return new ObjectId(id);
    }

    protected String map(ObjectId id) {
        if (id == null) {
            return null;
        }

        return id.toHexString();
    }
}
