package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway;

import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.UserDocument;
import com.restcontrol.restcontrol_TC2.infra.mapper.UserMapper;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserGatewayImpl implements UserGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserGatewayImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User create(User user) {
        UserDocument document = userMapper.toDocument(user);
        UserDocument savedDocument = userRepository.save(document);
        return userMapper.toDomain(savedDocument);
    }

    @Override
    public User update(User user, String id) {
        UserDocument document = userMapper.toDocument(user);
        UserDocument savedDocument = userRepository.save(document);
        return userMapper.toDomain(savedDocument);
    }

    @Override
    public Optional<User> getById(String id) {
        return userRepository.findById(new ObjectId(id)).map(userMapper::toDomain);
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(new ObjectId(id));
    }
}
