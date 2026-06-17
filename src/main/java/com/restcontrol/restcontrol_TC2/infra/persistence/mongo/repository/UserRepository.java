package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository;

import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<UserDocument, UUID> {

}
