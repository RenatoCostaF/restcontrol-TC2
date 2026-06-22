package com.restcontrol.restcontrol_TC2.application.mapper.MenuItem;

import com.restcontrol.restcontrol_TC2.application.dto.MenuItem.request.MenuItemRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.MenuItem.request.UpdateMenuItemRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.MenuItem.response.MenuItemResponseDTO;
import com.restcontrol.restcontrol_TC2.application.dto.MenuItem.response.UpdateMenuItemResponseDTO;
import com.restcontrol.restcontrol_TC2.domain.Adapter.MenuItem.Input.MenuItemInput;
import com.restcontrol.restcontrol_TC2.domain.Adapter.MenuItem.Input.UpdateMenuItemInput;
import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import org.springframework.stereotype.Component;

@Component("applicationMenuItemMapper")
public class MenuItemMapper {

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
