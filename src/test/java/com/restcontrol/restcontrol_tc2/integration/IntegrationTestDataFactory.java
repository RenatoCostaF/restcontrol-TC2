package com.restcontrol.restcontrol_tc2.integration;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.RestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.MenuItemResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.RestaurantResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UserResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.UserTypeRepository;
import com.restcontrol.restcontrol_tc2.support.MenuItemTestFixtures;
import com.restcontrol.restcontrol_tc2.support.UserTestFixtures;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class IntegrationTestDataFactory {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserTypeRepository userTypeRepository;

    public IntegrationTestDataFactory(
            MockMvc mockMvc,
            ObjectMapper objectMapper,
            UserTypeRepository userTypeRepository
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userTypeRepository = userTypeRepository;
    }

    public String getUserTypeIdByCode(String code) {
        return userTypeRepository.findByCode(code)
                .orElseThrow(() -> new IllegalStateException("User type not seeded: " + code))
                .getId()
                .toHexString();
    }

    public String getCustomerUserTypeId() {
        return getUserTypeIdByCode(UserType.CUSTOMER_CODE);
    }

    public String getRestaurantOwnerUserTypeId() {
        return getUserTypeIdByCode(UserType.RESTAURANT_OWNER_CODE);
    }

    public UserResponseDTO createCustomerUser(String email) throws Exception {
        return createUser(
                UserTestFixtures.VALID_NAME,
                email,
                UserTestFixtures.VALID_PASSWORD,
                getCustomerUserTypeId()
        );
    }

    public UserResponseDTO createRestaurantOwnerUser(String email) throws Exception {
        return createUser(
                UserTestFixtures.VALID_NAME,
                email,
                UserTestFixtures.VALID_PASSWORD,
                getRestaurantOwnerUserTypeId()
        );
    }

    public UserResponseDTO createUser(String name, String email, String password, String userTypeId) throws Exception {
        var request = new CreateUserRequestDTO(name, email, password, userTypeId);

        var response = mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(response, UserResponseDTO.class);
    }

    public RestaurantResponseDTO createRestaurant(String ownerId) throws Exception {
        var request = new RestaurantRequestDTO(
                MenuItemTestFixtures.validRestaurant().getName(),
                MenuItemTestFixtures.validRestaurant().getAddress(),
                MenuItemTestFixtures.validRestaurant().getCuisineType(),
                MenuItemTestFixtures.validRestaurant().getOpeningHours(),
                ownerId
        );

        var response = mockMvc.perform(post("/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(response, RestaurantResponseDTO.class);
    }

    public RestaurantContext createRestaurantContext() throws Exception {
        var owner = createRestaurantOwnerUser("owner-" + System.nanoTime() + "@example.com");
        var restaurant = createRestaurant(owner.id());
        return new RestaurantContext(owner, restaurant);
    }

    public MenuItemResponseDTO createMenuItem(String restaurantId) throws Exception {
        var request = new CreateMenuItemRequestDTO(
                MenuItemTestFixtures.VALID_NAME,
                MenuItemTestFixtures.VALID_DESCRIPTION,
                MenuItemTestFixtures.VALID_PRICE,
                false,
                MenuItemTestFixtures.VALID_IMAGE_URL,
                restaurantId,
                true
        );

        var response = mockMvc.perform(post("/v1/menuitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(response, MenuItemResponseDTO.class);
    }

    public CreateUserRequestDTO invalidCreateUserRequest() {
        return new CreateUserRequestDTO("", "invalid-email", "short", "");
    }

    public UpdateUserRequestDTO validUpdateUserRequest(String userTypeId) {
        return new UpdateUserRequestDTO(
                "Jane Doe",
                "jane@example.com",
                "newpassword1",
                userTypeId
        );
    }

    public UpdateUserRequestDTO invalidUpdateUserRequest() {
        return new UpdateUserRequestDTO("", "invalid-email", "short", "");
    }

    public CreateMenuItemRequestDTO invalidCreateMenuItemRequest() {
        return new CreateMenuItemRequestDTO("", "", -1.0, null, "", "", null);
    }

    public UpdateMenuItemRequestDTO validUpdateMenuItemRequest(String restaurantId) {
        return new UpdateMenuItemRequestDTO(
                "Pepperoni Pizza",
                "Pizza with pepperoni",
                34.90,
                true,
                "https://example.com/pepperoni.jpg",
                restaurantId,
                false
        );
    }

    public UpdateMenuItemRequestDTO invalidUpdateMenuItemRequest() {
        return new UpdateMenuItemRequestDTO("", "", -1.0, null, "", "", null);
    }

    public record RestaurantContext(UserResponseDTO owner, RestaurantResponseDTO restaurant) {
    }
}
