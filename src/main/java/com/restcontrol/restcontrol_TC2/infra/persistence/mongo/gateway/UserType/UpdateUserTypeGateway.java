package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway.UserType;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.UpdateUserTypeInterface;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.UserTypeDocument;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.mapper.UserType.UserTypeMapper;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.UserTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserTypeGateway implements UpdateUserTypeInterface {

    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;

    public UpdateUserTypeGateway(UserTypeRepository userTypeRepository, UserTypeMapper userTypeMapper) {
        this.userTypeRepository = userTypeRepository;
        this.userTypeMapper = userTypeMapper;
    }

    @Override
    public UserType updateUserType(UserType userType, String id) {
        UserTypeDocument document = userTypeMapper.toDocument(userType);
        document.setId(id);

        UserTypeDocument savedDocument = userTypeRepository.save(document);
        return userTypeMapper.toDomain(savedDocument);
    }
}
