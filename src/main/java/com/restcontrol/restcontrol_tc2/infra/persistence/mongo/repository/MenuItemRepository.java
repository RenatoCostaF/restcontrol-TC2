package com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository;

import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.MenuItemDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MenuItemRepository extends MongoRepository<MenuItemDocument, ObjectId> {
}
