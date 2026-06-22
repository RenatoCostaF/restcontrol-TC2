package com.restcontrol.restcontrol_TC2.application.controller.MenuItem;

import com.restcontrol.restcontrol_TC2.application.dto.MenuItem.request.MenuItemRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.MenuItem.request.UpdateMenuItemRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.MenuItem.response.MenuItemResponseDTO;
import com.restcontrol.restcontrol_TC2.application.dto.MenuItem.response.UpdateMenuItemResponseDTO;
import com.restcontrol.restcontrol_TC2.application.mapper.MenuItem.MenuItemMapper;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.CreateMenuItemUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.DeleteMenuItemUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.GetByIdMenuItemUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.UpdateMenuItemUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/menu-items")
public class MenuItemController {

    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final GetByIdMenuItemUseCase getByIdMenuItemUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    public MenuItemController(
            CreateMenuItemUseCase createMenuItemUseCase,
            GetByIdMenuItemUseCase getByIdMenuItemUseCase,
            UpdateMenuItemUseCase updateMenuItemUseCase,
            DeleteMenuItemUseCase deleteMenuItemUseCase,
            MenuItemMapper menuItemMapper
    ) {
        this.createMenuItemUseCase = createMenuItemUseCase;
        this.getByIdMenuItemUseCase = getByIdMenuItemUseCase;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
        this.menuItemMapper = menuItemMapper;
    }

    @PostMapping
    public MenuItemResponseDTO create(
            @RequestBody MenuItemRequestDTO menuItemRequestDTO
    ) {
        var menuItemInput = menuItemMapper.toMenuItemInput(menuItemRequestDTO);
        var menuItem = createMenuItemUseCase.execute(menuItemInput);
        return menuItemMapper.toMenuItemResponseDTO(menuItem);
    }

    @GetMapping("/{id}")
    public MenuItemResponseDTO getById(
            @PathVariable String id
    ) {
        var menuItem = getByIdMenuItemUseCase.execute(id);
        return menuItemMapper.toMenuItemResponseDTO(menuItem);
    }

    @PutMapping("/{id}")
    public UpdateMenuItemResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateMenuItemRequestDTO updateMenuItemRequestDTO
    ) {
        var updateMenuItemInput = menuItemMapper.toUpdateMenuItemInput(updateMenuItemRequestDTO);
        var menuItem = updateMenuItemUseCase.execute(updateMenuItemInput, id);
        return menuItemMapper.toUpdateMenuItemResponseDTO(menuItem);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id
    ) {
        deleteMenuItemUseCase.execute(id);
    }
}
