package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.mapper;

import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.UserDocument;
import org.springframework.stereotype.Component;

@Component("mongoUserMapper")
public class UserMapper {

    public UserDocument toDocument(User user) {
        UserDocument document = new UserDocument();
        if (user.getId() != null) {
            document.setId(user.getId());
        }
        document.setName(user.getName());
        document.setEmail(user.getEmail());
        document.setPassword(user.getPassword());
        document.setUserTypeId(user.getUserType().getId());
        document.setUserTypeName(user.getUserType().getName());
        return document;
    }

    public User toDomain(UserDocument document) {
        UserType userType = UserType.create(
                document.getUserTypeId(),
                document.getUserTypeName()
        );

        User user = User.create(
                document.getName(),
                document.getEmail(),
                document.getPassword(),
                userType
        );

        if (document.getId() != null) {
            user.setId(document.getId());
        }

        return user;
    }
}
