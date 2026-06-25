package com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository;

import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.RestaurantDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestaurantRepository extends MongoRepository<RestaurantDocument, ObjectId> {

    List<RestaurantDocument> findByName(String name);

}
