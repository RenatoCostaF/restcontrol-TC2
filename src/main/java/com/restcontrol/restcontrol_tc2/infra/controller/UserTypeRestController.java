package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.controller.UserTypeController;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserTypeRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserTypeRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UserTypeResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.UserTypeMapper;
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
@RequestMapping("/v1/user-types")
@Tag(name = "User Types", description = "Controller para CRUD de tipos de usuário (camada infra)")
public class UserTypeRestController {

    private final UserTypeController userTypeController;
    private final UserTypeMapper userTypeMapper;

    public UserTypeRestController(UserTypeController userTypeController, UserTypeMapper userTypeMapper) {
        this.userTypeController = userTypeController;
        this.userTypeMapper = userTypeMapper;
    }

    @Operation(
            summary = "Criar novo tipo de usuário",
            description = "Cria um novo tipo de usuário validando os dados de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de usuário criado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserTypeResponseDTO.class)
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
                                              "path": "/v1/user-types",
                                              "errors": {
                                                "name": "must not be blank"
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao criar tipo de usuário",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PostMapping
    public UserTypeResponseDTO create(
            @Valid @RequestBody CreateUserTypeRequestDTO createUserTypeRequestDTO
    ) {
        var userTypeInput = userTypeMapper.toUserTypeInput(createUserTypeRequestDTO);
        var userType = userTypeController.create(userTypeInput);
        return userTypeMapper.toUserTypeResponseDTO(userType);
    }

    @Operation(
            summary = "Atualizar tipo de usuário",
            description = "Atualiza os dados do tipo de usuário correspondente ao identificador informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de usuário atualizado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserTypeResponseDTO.class)
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
                    description = "Tipo de usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao atualizar tipo de usuário",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public UserTypeResponseDTO update(
            @Parameter(description = "Identificador do tipo de usuário", example = "507f1f77bcf86cd799439011")
            @PathVariable String id,
            @Valid @RequestBody UpdateUserTypeRequestDTO updateUserTypeRequestDTO
    ) {
        var updateUserTypeInput = userTypeMapper.toUpdateUserTypeInput(updateUserTypeRequestDTO);
        var userType = userTypeController.update(updateUserTypeInput, id);
        return userTypeMapper.toUserTypeResponseDTO(userType);
    }

    @Operation(
            summary = "Listar todos os tipos de usuário",
            description = "Retorna a lista completa de tipos de usuário cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipos de usuário retornados com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserTypeResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao listar tipos de usuário",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping
    public List<UserTypeResponseDTO> listAll() {
        return userTypeController.listAll()
                .stream()
                .map(userTypeMapper::toUserTypeResponseDTO)
                .toList();
    }

    @Operation(
            summary = "Buscar tipo de usuário por ID",
            description = "Retorna os dados do tipo de usuário correspondente ao identificador informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de usuário encontrado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserTypeResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "user-type-not-found",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Not Found",
                                              "status": 404,
                                              "detail": "UserType not found",
                                              "path": "/v1/user-types/507f1f77bcf86cd799439011"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao buscar tipo de usuário",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public UserTypeResponseDTO getById(
            @Parameter(description = "Identificador do tipo de usuário", example = "507f1f77bcf86cd799439011")
            @PathVariable String id
    ) {
        var userType = userTypeController.getById(id);
        return userTypeMapper.toUserTypeResponseDTO(userType);
    }

    @Operation(
            summary = "Remover tipo de usuário",
            description = "Remove o tipo de usuário correspondente ao identificador informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário removido com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado ao remover tipo de usuário",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Identificador do tipo de usuário", example = "507f1f77bcf86cd799439011")
            @PathVariable String id
    ) {
        userTypeController.delete(id);
    }
}
