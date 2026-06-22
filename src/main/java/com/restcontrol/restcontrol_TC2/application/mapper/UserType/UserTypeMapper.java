package com.restcontrol.restcontrol_TC2.application.mapper.UserType;

import com.restcontrol.restcontrol_TC2.application.dto.UserType.request.UpdateUserTypeRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.UserType.request.UserTypeRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.UserType.response.UpdateUserTypeResponseDTO;
import com.restcontrol.restcontrol_TC2.application.dto.UserType.response.UserTypeResponseDTO;
import com.restcontrol.restcontrol_TC2.domain.Adapter.UserType.Input.UpdateUserTypeInput;
import com.restcontrol.restcontrol_TC2.domain.Adapter.UserType.Input.UserTypeInput;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import org.springframework.stereotype.Component;

@Component("applicationUserTypeMapper")
public class UserTypeMapper {

    public UserTypeInput toUserTypeInput(UserTypeRequestDTO requestDTO) {
        return new UserTypeInput(requestDTO.name());
    }

    public UpdateUserTypeInput toUpdateUserTypeInput(UpdateUserTypeRequestDTO requestDTO) {
        return new UpdateUserTypeInput(requestDTO.name());
    }

    public UserTypeResponseDTO toUserTypeResponseDTO(UserType userType) {
        return new UserTypeResponseDTO(
                userType.getId(),
                userType.getName()
        );
    }

    public UpdateUserTypeResponseDTO toUpdateUserTypeResponseDTO(UserType userType) {
        return new UpdateUserTypeResponseDTO(
                userType.getId(),
                userType.getName()
        );
    }
}
