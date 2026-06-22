package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository;

import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.MenuItemDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MenuItemRepository extends MongoRepository<MenuItemDocument, String> {
}
