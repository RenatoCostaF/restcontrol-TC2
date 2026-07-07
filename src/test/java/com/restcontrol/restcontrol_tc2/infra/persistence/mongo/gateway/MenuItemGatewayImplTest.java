package com.restcontrol.restcontrol_tc2.infra.persistence.mongo.gateway;

import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidObjectIdException;
import com.restcontrol.restcontrol_tc2.infra.mapper.MenuItemMapper;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.MenuItemDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.MenuItemRepository;
import com.restcontrol.restcontrol_tc2.support.MenuItemTestFixtures;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Gateway de persistência - MenuItem")
class MenuItemGatewayImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private MenuItemMapper menuItemMapper;

    @InjectMocks
    private MenuItemGatewayImpl menuItemGateway;

    private MenuItem menuItem;
    private MenuItemDocument document;
    private ObjectId objectId;

    @BeforeEach
    void setUp() {
        menuItem = MenuItemTestFixtures.validMenuItem();
        objectId = new ObjectId(menuItem.getId());
        document = new MenuItemDocument();
        document.setId(objectId);
        document.setName(menuItem.getName());
        document.setDescription(menuItem.getDescription());
        document.setPrice(menuItem.getPrice());
        document.setAvailableOnlyInRestaurant(menuItem.getAvailableOnlyInRestaurant());
        document.setImageUrl(menuItem.getImageUrl());
        document.setRestaurantId(menuItem.getRestaurantId());
        document.setActive(menuItem.getActive());
    }

    @Test
    @DisplayName("Deve criar item de cardápio no repositório")
    void shouldCreateMenuItem() {
        when(menuItemMapper.toDocument(menuItem)).thenReturn(document);
        when(menuItemRepository.save(document)).thenReturn(document);
        when(menuItemMapper.toDomain(document)).thenReturn(menuItem);

        MenuItem result = menuItemGateway.create(menuItem);

        assertEquals(menuItem, result);
        verify(menuItemMapper).toDocument(menuItem);
        verify(menuItemRepository).save(document);
        verify(menuItemMapper).toDomain(document);
    }

    @Test
    @DisplayName("Deve atualizar item de cardápio no repositório")
    void shouldUpdateMenuItem() {
        when(menuItemMapper.toDocument(menuItem)).thenReturn(document);
        when(menuItemRepository.save(document)).thenReturn(document);
        when(menuItemMapper.toDomain(document)).thenReturn(menuItem);

        MenuItem result = menuItemGateway.update(menuItem);

        assertEquals(menuItem, result);
        verify(menuItemRepository).save(document);
    }

    @Test
    @DisplayName("Deve listar todos os itens de cardápio")
    void shouldListAllMenuItems() {
        when(menuItemRepository.findAll()).thenReturn(List.of(document));
        when(menuItemMapper.toDomain(document)).thenReturn(menuItem);

        List<MenuItem> result = menuItemGateway.listAll();

        assertEquals(1, result.size());
        assertEquals(menuItem, result.getFirst());
    }

    @Test
    @DisplayName("Deve buscar item de cardápio por ID")
    void shouldGetMenuItemById() {
        when(menuItemRepository.findById(objectId)).thenReturn(Optional.of(document));
        when(menuItemMapper.toDomain(document)).thenReturn(menuItem);

        Optional<MenuItem> result = menuItemGateway.getById(menuItem.getId());

        assertTrue(result.isPresent());
        assertEquals(menuItem, result.get());
    }

    @Test
    @DisplayName("Deve retornar vazio quando item não for encontrado")
    void shouldReturnEmptyWhenMenuItemNotFound() {
        when(menuItemRepository.findById(objectId)).thenReturn(Optional.empty());

        Optional<MenuItem> result = menuItemGateway.getById(menuItem.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deve remover item de cardápio por ID")
    void shouldDeleteMenuItem() {
        menuItemGateway.delete(menuItem.getId());

        verify(menuItemRepository).deleteById(objectId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ID for inválido")
    void shouldThrowWhenIdIsInvalid() {
        assertThrows(InvalidObjectIdException.class, () -> menuItemGateway.getById("invalid-id"));
        assertThrows(InvalidObjectIdException.class, () -> menuItemGateway.delete(""));
        assertThrows(InvalidObjectIdException.class, () -> menuItemGateway.delete("invalid-id"));
    }
}
