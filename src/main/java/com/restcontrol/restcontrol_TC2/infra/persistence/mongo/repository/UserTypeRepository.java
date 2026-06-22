package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository;

import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.UserTypeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTypeRepository extends MongoRepository<UserTypeDocument, String> {
}
