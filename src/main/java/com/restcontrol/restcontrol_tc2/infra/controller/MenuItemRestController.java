package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.controller.MenuItemController;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.MenuItemResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.MenuItemMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/menuitems")
public class MenuItemRestController {

    private final MenuItemController menuItemController;
    private final MenuItemMapper menuItemMapper;

    public MenuItemRestController(MenuItemController menuItemController, MenuItemMapper menuItemMapper) {
        this.menuItemController = menuItemController;
        this.menuItemMapper = menuItemMapper;
    }

    @PostMapping
    public MenuItemResponseDTO create(
            @Valid @RequestBody CreateMenuItemRequestDTO createMenuItemRequestDTO
    ) {
        var menuItemInput = menuItemMapper.toMenuItemInput(createMenuItemRequestDTO);
        var menuItem = menuItemController.create(menuItemInput);
        return menuItemMapper.toMenuItemResponseDTO(menuItem);
    }

    @PutMapping("/{id}")
    public MenuItemResponseDTO update(
            @PathVariable String id,
            @Valid @RequestBody UpdateMenuItemRequestDTO updateMenuItemRequestDTO
    ) {
        var updateMenuItemInput = menuItemMapper.toUpdateMenuItemInput(updateMenuItemRequestDTO);
        var menuItem = menuItemController.update(updateMenuItemInput, id);
        return menuItemMapper.toMenuItemResponseDTO(menuItem);
    }

    @GetMapping("/{id}")
    public MenuItemResponseDTO getById(
            @PathVariable String id
    ) {
        var menuItem = menuItemController.getById(id);
        return menuItemMapper.toMenuItemResponseDTO(menuItem);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id
    ) {
        menuItemController.delete(id);
    }
}
