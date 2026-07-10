package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.controller.RestaurantController;
import com.restcontrol.restcontrol_tc2.infra.dto.request.RestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateRestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.RestaurantResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.RestaurantMapper;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/v1/restaurants")
@Tag(name = "Restaurant", description = "Controller para CRUD de restaurantes (camada infra)")
public class RestaurantRestController {

    private final RestaurantController restaurantController;
    private final RestaurantMapper restaurantMapper;

    public RestaurantRestController(RestaurantController restaurantController, RestaurantMapper restaurantMapper) {
        this.restaurantController = restaurantController;
        this.restaurantMapper = restaurantMapper;
    }

    @Operation(
            summary = "Criar novo restaurante",
            description = "Cria um novo restaurante e valida os dados de entrada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Restaurante criado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Payload inválido ou regras de domínio violadas",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "validation-error",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Bad Request",
                                              "status": 400,
                                              "detail": "Request validation failed",
                                              "path": "/v1/restaurants"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário proprietário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao criar restaurante",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PostMapping
    public RestaurantResponseDTO create(
            @Valid @RequestBody RestaurantRequestDTO restaurantRequestDTO
    ) {
        var restaurantInput = restaurantMapper.toCreateRestaurantInput(restaurantRequestDTO);
        var restaurant = restaurantController.create(restaurantInput);
        return restaurantMapper.toRestaurantResponseDTO(restaurant);
    }

    @Operation(
            summary = "Atualizar restaurante",
            description = "Atualiza um restaurante e valida os dados de entrada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Restaurante atualizado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Payload inválido ou regras de domínio violadas",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "restaurant-not-found",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Not Found",
                                              "status": 404,
                                              "detail": "Restaurant not found",
                                              "path": "/v1/restaurants/507f1f77bcf86cd799439011"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao atualizar restaurante",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public RestaurantResponseDTO update(
            @Parameter(description = "Identificador do restaurante", example = "507f1f77bcf86cd799439011")
            @PathVariable String id,
            @Valid @RequestBody UpdateRestaurantRequestDTO updateRestaurantRequestDTO
    ) {
        var updateRestaurantInput = restaurantMapper.toUpdateRestaurantInput(updateRestaurantRequestDTO);
        var restaurant = restaurantController.update(updateRestaurantInput, id);
        return restaurantMapper.toRestaurantResponseDTO(restaurant);
    }

    @Operation(
            summary = "Buscar restaurantes",
            description = "Apresenta uma lista com restaurantes existentes"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de restaurantes retornada com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao buscar restaurantes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping
    public List<RestaurantResponseDTO> listAll() {
        return restaurantController.listAll()
                .stream()
                .map(restaurantMapper::toRestaurantResponseDTO)
                .toList();
    }

    @Operation(
            summary = "Buscar restaurante pelo nome",
            description = "Retorna o restaurante que possui o nome indicado como parâmetro"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Restaurante encontrado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "restaurant-not-found",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Not Found",
                                              "status": 404,
                                              "detail": "Restaurant not found",
                                              "path": "/v1/restaurants"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao buscar restaurante",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping("/search")
    public RestaurantResponseDTO getByName(
            @Parameter(description = "Nome do restaurante", example = "Restaurante do João")
            @RequestParam String name
    ) {
        var restaurant = restaurantController.getByName(name);
        return restaurantMapper.toRestaurantResponseDTO(restaurant);
    }

    @Operation(
            summary = "Buscar restaurante pelo ID",
            description = "Retorna o restaurante correspondente ao identificador informado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Restaurante encontrado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "restaurant-not-found",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Not Found",
                                              "status": 404,
                                              "detail": "Restaurant not found",
                                              "path": "/v1/restaurants/507f1f77bcf86cd799439011"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao buscar restaurante",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public RestaurantResponseDTO getById(
            @Parameter(description = "Identificador do restaurante", example = "507f1f77bcf86cd799439011")
            @PathVariable String id
    ) {
        var restaurant = restaurantController.getById(id);
        return restaurantMapper.toRestaurantResponseDTO(restaurant);
    }

    @Operation(
            summary = "Deletar restaurante pelo ID",
            description = "Remove o restaurante correspondente ao identificador informado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Restaurante removido com sucesso"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não é o proprietário do restaurante",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante ou usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao remover restaurante",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Identificador do restaurante", example = "507f1f77bcf86cd799439011")
            @PathVariable String id,
            @Parameter(description = "Identificador do usuário que está realizando a operação", example = "507f1f77bcf86cd799439011")
            @RequestParam String runningUserId
    ) {
        restaurantController.delete(id, runningUserId);
    }

}
