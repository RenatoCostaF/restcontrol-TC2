package com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository;

import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserTypeDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserTypeRepository extends MongoRepository<UserTypeDocument, ObjectId> {

    Optional<UserTypeDocument> findByCode(String code);
}
