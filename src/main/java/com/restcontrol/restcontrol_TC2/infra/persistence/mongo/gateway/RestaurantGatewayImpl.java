package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway;

import com.restcontrol.restcontrol_TC2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_TC2.domain.gateway.RestaurantGateway;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.RestaurantDocument;
import com.restcontrol.restcontrol_TC2.infra.mapper.RestaurantMapper;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.RestaurantRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestaurantGatewayImpl implements RestaurantGateway {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantGatewayImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        RestaurantDocument document = restaurantMapper.toDocument(restaurant);
        RestaurantDocument savedDocument = restaurantRepository.save(document);
        return restaurantMapper.toDomain(savedDocument);
    }

    @Override
    public Restaurant update(Restaurant restaurant, String id) {
        RestaurantDocument document = restaurantMapper.toDocument(restaurant);
        document.setId(new ObjectId(id));
        RestaurantDocument savedDocument = restaurantRepository.save(document);
        return restaurantMapper.toDomain(savedDocument);
    }

    @Override
    public Optional<Restaurant> getByName(String name) {
        return restaurantRepository.findByName(name)
                .stream()
                .findFirst()
                .map(restaurantMapper::toDomain);
    }

    @Override
    public Optional<Restaurant> getById(String id) {
        return restaurantRepository.findById(new ObjectId(id)).map(restaurantMapper::toDomain);
    }

    @Override
    public void delete(String id) {
        restaurantRepository.deleteById(new ObjectId(id));
    }
}
