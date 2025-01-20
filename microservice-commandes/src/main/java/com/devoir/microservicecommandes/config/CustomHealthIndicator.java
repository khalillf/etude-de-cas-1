package com.devoir.microservicecommandes.config;

import com.devoir.microservicecommandes.repositories.CommandeRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
@CrossOrigin(origins = "http://localhost:3000")
public class CustomHealthIndicator implements HealthIndicator {

    private final CommandeRepository commandeRepository;

    public CustomHealthIndicator(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public Health health() {
        try {

            long count = commandeRepository.count();

            if (count > 0) {
                return Health.up()
                        .withDetail("message", "Le microservice est en bonne santé. Il y a des commandes dans la base de données.")
                        .build();
            } else {
                return Health.up()
                        .withDetail("message", "Le microservice est en bonne santé, mais aucune commande n'est présente dans la base de données.")
                        .build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withDetail("message", "Le microservice est en panne : " + e.getMessage())
                    .build();
        }
    }
}