package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.controller.MenuItemController;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.MenuItemResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.MenuItemMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/menuitems")
@Tag(name = "Menu Items", description = "Controller para CRUD de itens de cardápio (camada infra)")
public class MenuItemRestController {

    private final MenuItemController menuItemController;
    private final MenuItemMapper menuItemMapper;

    public MenuItemRestController(MenuItemController menuItemController, MenuItemMapper menuItemMapper) {
        this.menuItemController = menuItemController;
        this.menuItemMapper = menuItemMapper;
    }

    @Operation(
            summary = "Criar novo item de cardápio",
            description = "Cria um novo item de cardápio validando os dados de entrada e a existência do restaurante informado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item de cardápio criado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenuItemResponseDTO.class)
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
                                              "path": "/v1/menuitems",
                                              "errors": {
                                                "price": must be greater than 0"
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao criar item de cardápio",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PostMapping
    public MenuItemResponseDTO create(
            @Valid @RequestBody CreateMenuItemRequestDTO createMenuItemRequestDTO
    ) {
        var menuItemInput = menuItemMapper.toMenuItemInput(createMenuItemRequestDTO);
        var menuItem = menuItemController.create(menuItemInput);
        return menuItemMapper.toMenuItemResponseDTO(menuItem);
    }

    @Operation(
            summary = "Atualizar item de cardápio",
            description = "Atualiza os dados do item de cardápio correspondente ao identificador informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item de cardápio atualizado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenuItemResponseDTO.class)
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
                    description = "Item de cardápio ou restaurante não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao atualizar item de cardápio",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public MenuItemResponseDTO update(
            @Parameter(description = "Identificador do item de cardápio", example = "507f1f77bcf86cd799439011")
            @PathVariable String id,
            @Valid @RequestBody UpdateMenuItemRequestDTO updateMenuItemRequestDTO
    ) {
        var updateMenuItemInput = menuItemMapper.toUpdateMenuItemInput(updateMenuItemRequestDTO);
        var menuItem = menuItemController.update(updateMenuItemInput, id);
        return menuItemMapper.toMenuItemResponseDTO(menuItem);
    }

    @Operation(
            summary = "Listar todos os itens de cardápio",
            description = "Retorna a lista completa de itens de cardápio cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Itens de cardápio retornados com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = MenuItemResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao listar itens de cardápio",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping
    public List<MenuItemResponseDTO> listAll() {
        return menuItemController.listAll()
                .stream()
                .map(menuItemMapper::toMenuItemResponseDTO)
                .toList();
    }

    @Operation(
            summary = "Buscar item de cardápio por ID",
            description = "Retorna os dados do item de cardápio correspondente ao identificador informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item de cardápio encontrado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenuItemResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item de cardápio não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "menu-item-not-found",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Not Found",
                                              "status": 404,
                                              "detail": "Menu item not found",
                                              "path": "/v1/menuitems/507f1f77bcf86cd799439011"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao buscar item de cardápio",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public MenuItemResponseDTO getById(
            @Parameter(description = "Identificador do item de cardápio", example = "507f1f77bcf86cd799439011")
            @PathVariable String id
    ) {
        var menuItem = menuItemController.getById(id);
        return menuItemMapper.toMenuItemResponseDTO(menuItem);
    }

    @Operation(
            summary = "Remover item de cardápio",
            description = "Remove o item de cardápio correspondente ao identificador informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de cardápio removido com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item de cardápio não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao remover item de cardápio",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Identificador do item de cardápio", example = "507f1f77bcf86cd799439011")
            @PathVariable String id
    ) {
        menuItemController.delete(id);
    }
}
