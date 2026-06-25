package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.usecase.input.DeleteRestaurantInput;
import com.restcontrol.restcontrol_tc2.domain.port.input.RestaurantInputPort;
import com.restcontrol.restcontrol_tc2.infra.dto.request.RestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateRestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.RestaurantResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UpdateRestaurantResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.RestaurantMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/restaurants")
public class RestaurantController {

    private final RestaurantMapper restaurantMapper;
    private final RestaurantInputPort restaurantUseCase;

    public RestaurantController(RestaurantMapper restaurantMapper, RestaurantInputPort restaurantUseCase) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantUseCase = restaurantUseCase;
    }


    @PostMapping
    public RestaurantResponseDTO create(
            @RequestBody RestaurantRequestDTO restaurantRequestDTO
    ) {
        var restaurantInput = restaurantMapper.toCreateRestaurantInput(restaurantRequestDTO);
        var restaurant = restaurantUseCase.create(restaurantInput);
        return restaurantMapper.toRestaurantResponseDTO(restaurant);
    }

    @PutMapping("/{id}")
    public UpdateRestaurantResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateRestaurantRequestDTO updateRestaurantRequestDTO
    ) {
        var updateRestaurantInput = restaurantMapper.toUpdateRestaurantInput(updateRestaurantRequestDTO);
        var restaurant = restaurantUseCase.update(updateRestaurantInput, id);
        return restaurantMapper.toUpdateRestaurantResponseDTO(restaurant);
    }

    @GetMapping("/{id}")
    public RestaurantResponseDTO getById(
            @PathVariable String id
    ) {
        var restaurant = restaurantUseCase.getById(id);
        return restaurantMapper.toRestaurantResponseDTO(restaurant);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id,
            @RequestParam String runningUserId
    ) {
        restaurantUseCase.delete(new DeleteRestaurantInput(id, runningUserId));
    }

}
