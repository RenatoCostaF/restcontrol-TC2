package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.controller.RestaurantController;
import com.restcontrol.restcontrol_tc2.infra.dto.request.RestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateRestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.RestaurantResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.RestaurantMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/restaurants")
public class RestaurantRestController {

    private final RestaurantController restaurantController;
    private final RestaurantMapper restaurantMapper;

    public RestaurantRestController(RestaurantController restaurantController, RestaurantMapper restaurantMapper) {
        this.restaurantController = restaurantController;
        this.restaurantMapper = restaurantMapper;
    }

    @PostMapping
    public RestaurantResponseDTO create(
            @RequestBody RestaurantRequestDTO restaurantRequestDTO
    ) {
        var restaurantInput = restaurantMapper.toCreateRestaurantInput(restaurantRequestDTO);
        var restaurant = restaurantController.create(restaurantInput);
        return restaurantMapper.toRestaurantResponseDTO(restaurant);
    }

    @PutMapping("/{id}")
    public RestaurantResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateRestaurantRequestDTO updateRestaurantRequestDTO
    ) {
        var updateRestaurantInput = restaurantMapper.toUpdateRestaurantInput(updateRestaurantRequestDTO);
        var restaurant = restaurantController.update(updateRestaurantInput, id);
        return restaurantMapper.toRestaurantResponseDTO(restaurant);
    }

    @GetMapping
    public RestaurantResponseDTO getByName(
            @RequestParam String name
    ) {
        var restaurant = restaurantController.getByName(name);
        return restaurantMapper.toRestaurantResponseDTO(restaurant);
    }

    @GetMapping("/{id}")
    public RestaurantResponseDTO getById(
            @PathVariable String id
    ) {
        var restaurant = restaurantController.getById(id);
        return restaurantMapper.toRestaurantResponseDTO(restaurant);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id,
            @RequestParam String runningUserId
    ) {
        restaurantController.delete(id, runningUserId);
    }

}
