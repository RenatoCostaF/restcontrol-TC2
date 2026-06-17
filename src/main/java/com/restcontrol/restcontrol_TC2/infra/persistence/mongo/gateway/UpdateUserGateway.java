package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway;

import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.UpdateUserInterface;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.UserDocument;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.mapper.UserMapper;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateUserGateway implements UpdateUserInterface {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UpdateUserGateway(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User updateUser(User user, UUID id) {
        UserDocument document = userMapper.toDocument(user);
        document.setId(id);

        UserDocument savedDocument = userRepository.save(document);
        return userMapper.toDomain(savedDocument);
    }
}
