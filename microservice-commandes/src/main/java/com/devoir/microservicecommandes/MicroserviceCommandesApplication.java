package com.devoir.microservicecommandes;

import com.devoir.microservicecommandes.config.ApplicationPropertiesConfiguration;
import com.devoir.microservicecommandes.models.Commande;
import com.devoir.microservicecommandes.repositories.CommandeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@RefreshScope
public class MicroserviceCommandesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceCommandesApplication.class, args);
    }
    @Bean
    public CommandLineRunner initData(CommandeRepository commandeRepository) {
        return args -> {
            Date currentDate = new Date();
            int commandesLast = 15;

            for (int i = 1; i <= commandesLast; i++) {
                Commande commande = new Commande();
                commande.setDescription("Commande " + i);
                commande.setQuantite(i * 2);
                commande.setDate(new Date(currentDate.getTime()));
                commande.setMontant(100.0 * i);

                commandeRepository.save(commande);


                currentDate.setTime(currentDate.getTime() + 24 * 60 * 60 * 1000);
            }
        };
    }
}
