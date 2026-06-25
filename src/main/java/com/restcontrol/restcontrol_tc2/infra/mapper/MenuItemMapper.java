package com.restcontrol.restcontrol_tc2.infra.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.MenuItemResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UpdateMenuItemResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.MenuItemDocument;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class MenuItemMapper {

    public abstract MenuItemDocument toDocument(MenuItem menuItem);

    public abstract MenuItem toDomain(MenuItemDocument document);

    public abstract CreateMenuItemInputDTO toMenuItemInput(CreateMenuItemRequestDTO createMenuItemRequestDTO);

    public abstract MenuItemResponseDTO toMenuItemResponseDTO(MenuItem menuItem);

    public abstract UpdateMenuItemInputDTO toUpdateMenuItemInput(UpdateMenuItemRequestDTO updateMenuItemRequestDTO);

    public abstract UpdateMenuItemResponseDTO toUpdateMenuItemResponseDTO(MenuItem menuItem);

    protected ObjectId map(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }

        return new ObjectId(id);
    }

    protected String map(ObjectId id) {
        if (id == null) {
            return null;
        }

        return id.toHexString();
    }
}
