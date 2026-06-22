package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.mapper.UserType;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.UserTypeDocument;
import org.springframework.stereotype.Component;

@Component("mongoUserTypeMapper")
public class UserTypeMapper {

    public UserTypeDocument toDocument(UserType userType) {
        UserTypeDocument document = new UserTypeDocument();
        if (userType.getId() != null) {
            document.setId(userType.getId());
        }
        document.setName(userType.getName());
        return document;
    }

    public UserType toDomain(UserTypeDocument document) {
        UserType userType = UserType.create(document.getName());

        if (document.getId() != null) {
            userType.setId(document.getId());
        }

        return userType;
    }
}
