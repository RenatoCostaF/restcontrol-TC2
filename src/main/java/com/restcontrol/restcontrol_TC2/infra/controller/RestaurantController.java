package com.restcontrol.restcontrol_TC2.infra.controller;

import com.restcontrol.restcontrol_TC2.domain.useCase.RestaurantUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserUseCase;
import com.restcontrol.restcontrol_TC2.infra.dto.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.request.UserRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.UpdateUserResponseDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.UserResponseDTO;
import com.restcontrol.restcontrol_TC2.infra.mapper.RestaurantMapper;
import com.restcontrol.restcontrol_TC2.infra.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/restaurants")
public class RestaurantController {

    private final RestaurantMapper restaurantMapper;
    private final RestaurantUseCase restaurantUseCase;

    public RestaurantController(RestaurantMapper restaurantMapper, RestaurantUseCase restaurantUseCase) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantUseCase = restaurantUseCase;
    }


    @PostMapping
    public UserResponseDTO create(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        var userInput = restaurantMapper.toUserInput(userRequestDTO);
        var user = restaurantUseCase.create(userInput);
        return restaurantMapper.toUserResponseDTO(user);
    }

    @PutMapping("/{id}")
    public UpdateUserResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateUserRequestDTO updateUserRequestDTO
    ) {
        var updateUserInput = restaurantMapper.toUpdateUserInput(updateUserRequestDTO);
        var user = restaurantUseCase.update(updateUserInput, id);
        return restaurantMapper.toUpdateUserResponseDTO(user);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getById(
            @PathVariable String id
    ) {
        var user = restaurantUseCase.getById(id);
        return restaurantMapper.toUserResponseDTO(user);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id
    ) {
        restaurantUseCase.delete(id);
    }

}
