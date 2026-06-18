package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository;

import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, String> {
}
