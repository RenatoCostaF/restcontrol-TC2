package com.restcontrol.restcontrol_TC2.infra.controller;

import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItemUseCase;
import com.restcontrol.restcontrol_TC2.infra.dto.request.MenuItemRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.request.UpdateMenuItemRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.MenuItemResponseDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.UpdateMenuItemResponseDTO;
import com.restcontrol.restcontrol_TC2.infra.mapper.MenuItemMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/menuitems")
public class MenuItemController {

    private final MenuItemMapper menuItemMapper;
    private final MenuItemUseCase menuItemUseCase;

    public MenuItemController(MenuItemMapper menuItemMapper, MenuItemUseCase menuItemUseCase) {
        this.menuItemMapper = menuItemMapper;
        this.menuItemUseCase = menuItemUseCase;
    }

    @PostMapping
    public MenuItemResponseDTO create(
            @RequestBody MenuItemRequestDTO menuItemRequestDTO
    ) {
        var menuItemInput = menuItemMapper.toMenuItemInput(menuItemRequestDTO);
        var menuItem = menuItemUseCase.create(menuItemInput);
        return menuItemMapper.toMenuItemResponseDTO(menuItem);
    }

    @PutMapping("/{id}")
    public UpdateMenuItemResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateMenuItemRequestDTO updateMenuItemRequestDTO
    ) {
        var updateMenuItemInput = menuItemMapper.toUpdateMenuItemInput(updateMenuItemRequestDTO);
        var menuItem = menuItemUseCase.update(updateMenuItemInput, id);
        return menuItemMapper.toUpdateMenuItemResponseDTO(menuItem);
    }

    @GetMapping("/{id}")
    public MenuItemResponseDTO getById(
            @PathVariable String id
    ) {
        var menuItem = menuItemUseCase.getById(id);
        return menuItemMapper.toMenuItemResponseDTO(menuItem);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id
    ) {
        menuItemUseCase.delete(id);
    }
}
