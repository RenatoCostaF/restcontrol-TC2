package com.restcontrol.restcontrol_tc2.infra.config;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserTypeDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.UserTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeSeedConfig {

    @Bean
    public CommandLineRunner seedUserTypes(UserTypeRepository userTypeRepository) {
        return args -> {
            ensureUserType(userTypeRepository, "Cliente", "CUSTOMER");
            ensureUserType(userTypeRepository, "Dono de Restaurante", UserType.RESTAURANT_OWNER_CODE);
        };
    }

    private void ensureUserType(UserTypeRepository userTypeRepository, String name, String code) {
        if (userTypeRepository.findByCode(code).isPresent()) {
            return;
        }

        UserTypeDocument document = new UserTypeDocument();
        document.setName(name);
        document.setCode(code);
        userTypeRepository.save(document);
    }
}
