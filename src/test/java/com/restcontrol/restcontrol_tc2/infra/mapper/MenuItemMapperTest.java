package com.restcontrol.restcontrol_tc2.infra.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.MenuItemResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.MenuItemDocument;
import com.restcontrol.restcontrol_tc2.helper.MenuItemTestHelper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Mapper de infra - MenuItem")
class MenuItemMapperTest {

    private MenuItemMapper menuItemMapper;

    @BeforeEach
    void setUp() {
        menuItemMapper = Mappers.getMapper(MenuItemMapper.class);
    }

    @Test
    @DisplayName("Deve mapear MenuItem para MenuItemDocument")
    void shouldMapMenuItemToDocument() {
        MenuItem menuItem = MenuItemTestHelper.validMenuItem();

        MenuItemDocument document = menuItemMapper.toDocument(menuItem);

        assertEquals(new ObjectId(menuItem.getId()), document.getId());
        assertEquals(menuItem.getName(), document.getName());
        assertEquals(menuItem.getDescription(), document.getDescription());
        assertEquals(menuItem.getPrice(), document.getPrice());
        assertEquals(menuItem.getAvailableOnlyInRestaurant(), document.getAvailableOnlyInRestaurant());
        assertEquals(menuItem.getImageUrl(), document.getImageUrl());
        assertEquals(menuItem.getRestaurantId(), document.getRestaurantId());
        assertEquals(menuItem.getActive(), document.getActive());
    }

    @Test
    @DisplayName("Deve mapear MenuItem para MenuItemDocument com ID nulo")
    void shouldMapMenuItemToDocumentWithNullId() {
        MenuItem menuItem = new MenuItem(
                null,
                MenuItemTestHelper.VALID_NAME,
                MenuItemTestHelper.VALID_DESCRIPTION,
                MenuItemTestHelper.VALID_PRICE,
                false,
                MenuItemTestHelper.VALID_IMAGE_URL,
                MenuItemTestHelper.VALID_RESTAURANT_ID,
                true
        );

        MenuItemDocument document = menuItemMapper.toDocument(menuItem);

        assertNull(document.getId());
        assertEquals(menuItem.getName(), document.getName());
    }

    @Test
    @DisplayName("Deve mapear MenuItemDocument para MenuItem")
    void shouldMapDocumentToDomain() {
        ObjectId objectId = new ObjectId();
        MenuItemDocument document = new MenuItemDocument();
        document.setId(objectId);
        document.setName(MenuItemTestHelper.VALID_NAME);
        document.setDescription(MenuItemTestHelper.VALID_DESCRIPTION);
        document.setPrice(MenuItemTestHelper.VALID_PRICE);
        document.setAvailableOnlyInRestaurant(false);
        document.setImageUrl(MenuItemTestHelper.VALID_IMAGE_URL);
        document.setRestaurantId(MenuItemTestHelper.VALID_RESTAURANT_ID);
        document.setActive(true);

        MenuItem menuItem = menuItemMapper.toDomain(document);

        assertEquals(objectId.toHexString(), menuItem.getId());
        assertEquals(document.getName(), menuItem.getName());
        assertEquals(document.getDescription(), menuItem.getDescription());
        assertEquals(document.getPrice(), menuItem.getPrice());
        assertEquals(document.getAvailableOnlyInRestaurant(), menuItem.getAvailableOnlyInRestaurant());
        assertEquals(document.getImageUrl(), menuItem.getImageUrl());
        assertEquals(document.getRestaurantId(), menuItem.getRestaurantId());
        assertEquals(document.getActive(), menuItem.getActive());
    }

    @Test
    @DisplayName("Deve mapear MenuItemDocument para MenuItem com ID nulo")
    void shouldMapDocumentToDomainWithNullId() {
        MenuItemDocument document = new MenuItemDocument();
        document.setName(MenuItemTestHelper.VALID_NAME);
        document.setDescription(MenuItemTestHelper.VALID_DESCRIPTION);
        document.setPrice(MenuItemTestHelper.VALID_PRICE);
        document.setAvailableOnlyInRestaurant(false);
        document.setImageUrl(MenuItemTestHelper.VALID_IMAGE_URL);
        document.setRestaurantId(MenuItemTestHelper.VALID_RESTAURANT_ID);
        document.setActive(true);

        MenuItem menuItem = menuItemMapper.toDomain(document);

        assertNull(menuItem.getId());
    }

    @Test
    @DisplayName("Deve mapear CreateMenuItemRequestDTO para CreateMenuItemInputDTO")
    void shouldMapCreateMenuItemRequestToInput() {
        CreateMenuItemRequestDTO request = new CreateMenuItemRequestDTO(
                MenuItemTestHelper.VALID_NAME,
                MenuItemTestHelper.VALID_DESCRIPTION,
                MenuItemTestHelper.VALID_PRICE,
                false,
                MenuItemTestHelper.VALID_IMAGE_URL,
                MenuItemTestHelper.VALID_RESTAURANT_ID,
                true
        );

        CreateMenuItemInputDTO input = menuItemMapper.toMenuItemInput(request);

        assertNull(input.id());
        assertEquals(request.name(), input.name());
        assertEquals(request.description(), input.description());
        assertEquals(request.price(), input.price());
        assertEquals(request.availableOnlyInRestaurant(), input.availableOnlyInRestaurant());
        assertEquals(request.imageUrl(), input.imageUrl());
        assertEquals(request.restaurantId(), input.restaurantId());
        assertEquals(request.active(), input.active());
    }

    @Test
    @DisplayName("Deve mapear UpdateMenuItemRequestDTO para UpdateMenuItemInputDTO")
    void shouldMapUpdateMenuItemRequestToInput() {
        UpdateMenuItemRequestDTO request = new UpdateMenuItemRequestDTO(
                "Pepperoni Pizza",
                "Pizza with pepperoni",
                34.90,
                true,
                "https://example.com/pepperoni.jpg",
                MenuItemTestHelper.VALID_RESTAURANT_ID,
                false
        );

        UpdateMenuItemInputDTO input = menuItemMapper.toUpdateMenuItemInput(request);

        assertEquals(request.name(), input.name());
        assertEquals(request.description(), input.description());
        assertEquals(request.price(), input.price());
        assertEquals(request.availableOnlyInRestaurant(), input.availableOnlyInRestaurant());
        assertEquals(request.imageUrl(), input.imageUrl());
        assertEquals(request.restaurantId(), input.restaurantId());
        assertEquals(request.active(), input.active());
    }

    @Test
    @DisplayName("Deve mapear MenuItem para MenuItemDocument com ID em branco")
    void shouldMapMenuItemToDocumentWithBlankId() {
        MenuItem menuItem = new MenuItem(
                "",
                MenuItemTestHelper.VALID_NAME,
                MenuItemTestHelper.VALID_DESCRIPTION,
                MenuItemTestHelper.VALID_PRICE,
                false,
                MenuItemTestHelper.VALID_IMAGE_URL,
                MenuItemTestHelper.VALID_RESTAURANT_ID,
                true
        );

        MenuItemDocument document = menuItemMapper.toDocument(menuItem);

        assertNull(document.getId());
        assertEquals(menuItem.getName(), document.getName());
    }

    @Test
    @DisplayName("Deve mapear MenuItem para MenuItemResponseDTO")
    void shouldMapMenuItemToResponseDto() {
        MenuItem menuItem = MenuItemTestHelper.validMenuItem();

        MenuItemResponseDTO response = menuItemMapper.toMenuItemResponseDTO(menuItem);

        assertEquals(menuItem.getId(), response.id());
        assertEquals(menuItem.getName(), response.name());
        assertEquals(menuItem.getDescription(), response.description());
        assertEquals(menuItem.getPrice(), response.price());
        assertEquals(menuItem.getAvailableOnlyInRestaurant(), response.availableOnlyInRestaurant());
        assertEquals(menuItem.getImageUrl(), response.imageUrl());
        assertEquals(menuItem.getRestaurantId(), response.restaurantId());
        assertEquals(menuItem.getActive(), response.active());
    }
}
