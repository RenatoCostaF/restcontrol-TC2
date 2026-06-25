package com.restcontrol.restcontrol_tc2.infra.mapper;

import com.restcontrol.restcontrol_tc2.domain.usecase.input.MenuItemInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateMenuItemInput;
import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.infra.dto.request.MenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.MenuItemResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UpdateMenuItemResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.MenuItemDocument;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    public MenuItemDocument toDocument(MenuItem menuItem) {
        MenuItemDocument document = new MenuItemDocument();
        if (menuItem.getId() != null && !menuItem.getId().isBlank()) {
            document.setId(new org.bson.types.ObjectId(menuItem.getId()));
        }
        document.setName(menuItem.getName());
        document.setDescription(menuItem.getDescription());
        document.setPrice(menuItem.getPrice());
        document.setAvailableForDelivery(menuItem.getAvailableForDelivery());
        document.setImageUrl(menuItem.getImageUrl());
        document.setRestaurantId(menuItem.getRestaurantId());
        document.setActive(menuItem.getActive());
        return document;
    }

    public MenuItem toDomain(MenuItemDocument document) {
        return MenuItem.restore(
                document.getId() != null ? document.getId().toHexString() : null,
                document.getName(),
                document.getDescription(),
                document.getPrice(),
                document.getAvailableForDelivery(),
                document.getImageUrl(),
                document.getRestaurantId(),
                document.getActive()
        );
    }

    public MenuItemInput toMenuItemInput(MenuItemRequestDTO requestDTO) {
        return new MenuItemInput(
                requestDTO.name(),
                requestDTO.description(),
                requestDTO.price(),
                requestDTO.availableForDelivery(),
                requestDTO.imageUrl(),
                requestDTO.restaurantId(),
                requestDTO.isActive()
        );
    }

    public UpdateMenuItemInput toUpdateMenuItemInput(UpdateMenuItemRequestDTO requestDTO) {
        return new UpdateMenuItemInput(
                requestDTO.name(),
                requestDTO.description(),
                requestDTO.price(),
                requestDTO.availableForDelivery(),
                requestDTO.imageUrl(),
                requestDTO.restaurantId(),
                requestDTO.isActive()
        );
    }

    public MenuItemResponseDTO toMenuItemResponseDTO(MenuItem menuItem) {
        return new MenuItemResponseDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailableForDelivery(),
                menuItem.getImageUrl(),
                menuItem.getRestaurantId(),
                menuItem.getActive()
        );
    }

    public UpdateMenuItemResponseDTO toUpdateMenuItemResponseDTO(MenuItem menuItem) {
        return new UpdateMenuItemResponseDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailableForDelivery(),
                menuItem.getImageUrl(),
                menuItem.getRestaurantId(),
                menuItem.getActive()
        );
    }
}
