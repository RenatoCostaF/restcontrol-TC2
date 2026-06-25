package com.restcontrol.restcontrol_tc2.infra.persistence.mongo.gateway;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidObjectIdException;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_tc2.infra.mapper.UserTypeMapper;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserTypeDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.UserTypeRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserTypeGatewayImpl implements UserTypeGateway {

    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;

    public UserTypeGatewayImpl(UserTypeRepository userTypeRepository, UserTypeMapper userTypeMapper) {
        this.userTypeRepository = userTypeRepository;
        this.userTypeMapper = userTypeMapper;
    }

    @Override
    public UserType create(UserType userType) {
        UserTypeDocument document = userTypeMapper.toDocument(userType);
        UserTypeDocument savedDocument = userTypeRepository.save(document);
        return userTypeMapper.toDomain(savedDocument);
    }

    @Override
    public UserType update(UserType userType) {
        UserTypeDocument document = userTypeMapper.toDocument(userType);
        UserTypeDocument savedDocument = userTypeRepository.save(document);
        return userTypeMapper.toDomain(savedDocument);
    }

    @Override
    public List<UserType> listAll() {
        return userTypeRepository.findAll()
                .stream()
                .map(userTypeMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UserType> getById(String id) {
        return userTypeRepository.findById(toObjectId(id)).map(userTypeMapper::toDomain);
    }

    @Override
    public void delete(String id) {
        userTypeRepository.deleteById(toObjectId(id));
    }

    private ObjectId toObjectId(String id) {
        if (id == null || id.isBlank() || !ObjectId.isValid(id)) {
            throw new InvalidObjectIdException("Invalid id: " + id);
        }

        return new ObjectId(id);
    }
}
