package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway.User;

import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.CreateUserInterface;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.UserDocument;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.mapper.User.UserMapper;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateUserGateway implements CreateUserInterface {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CreateUserGateway(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(User user) {
        UserDocument document = userMapper.toDocument(user);
        UserDocument savedDocument = userRepository.save(document);
        return userMapper.toDomain(savedDocument);
    }
}
