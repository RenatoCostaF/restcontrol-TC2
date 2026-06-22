package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway.UserType;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.GetByIdUserTypeInterface;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.mapper.UserType.UserTypeMapper;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.UserTypeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetByIdUserTypeGateway implements GetByIdUserTypeInterface {

    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;

    public GetByIdUserTypeGateway(UserTypeRepository userTypeRepository, UserTypeMapper userTypeMapper) {
        this.userTypeRepository = userTypeRepository;
        this.userTypeMapper = userTypeMapper;
    }

    @Override
    public Optional<UserType> getById(String id) {
        return userTypeRepository.findById(id).map(userTypeMapper::toDomain);
    }
}
