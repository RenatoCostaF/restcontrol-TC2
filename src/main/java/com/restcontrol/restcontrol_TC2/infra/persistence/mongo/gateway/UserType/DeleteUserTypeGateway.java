package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway.UserType;

import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.DeleteUserTypeInterface;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.UserTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserTypeGateway implements DeleteUserTypeInterface {

    private final UserTypeRepository userTypeRepository;

    public DeleteUserTypeGateway(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public void deleteUserType(String id) {
        userTypeRepository.deleteById(id);
    }
}
