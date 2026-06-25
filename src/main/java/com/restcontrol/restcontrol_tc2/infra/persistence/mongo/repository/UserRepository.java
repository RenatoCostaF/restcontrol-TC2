package com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository;

import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, ObjectId> {
}
