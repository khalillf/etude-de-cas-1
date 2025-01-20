package com.devoir.microservicecommandes.controllers;

import com.devoir.microservicecommandes.config.ApplicationPropertiesConfiguration;
import com.devoir.microservicecommandes.exceptions.CommandeNotFoundException;
import com.devoir.microservicecommandes.models.Commande;
import com.devoir.microservicecommandes.repositories.CommandeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RefreshScope
@RequestMapping("/commandes")
@CrossOrigin(origins = "http://localhost:3000")
public class CommandeController implements HealthIndicator {

    private static final Logger logger = LoggerFactory.getLogger(CommandeController.class);

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ApplicationPropertiesConfiguration appProperties;

    @GetMapping
    public List<Commande> getLastCommandes() {
        logger.info("********* CommandeController getLastCommandes() ");
        Date date = new Date(System.currentTimeMillis() - appProperties.getCommandesLast() * 24 * 60 * 60 * 1000L);
        List<Commande> commandes = commandeRepository.findByDateAfter(date);

        if (commandes.isEmpty()) {
            throw new CommandeNotFoundException("Aucune commande n'est disponible");
        }

        List<Commande> listeLimitee = commandes.subList(0, Math.min(appProperties.getCommandesLast(), commandes.size()));
        return listeLimitee;
    }

    @GetMapping("/{id}")
    public Optional<Commande> getCommandeById(@PathVariable long id) {
        logger.info("********* CommandeController getCommandeById(@PathVariable long id) ");
        Optional<Commande> commande = commandeRepository.findById(id);

        if (commande.isEmpty()) {
            throw new CommandeNotFoundException("La commande correspondant à l'id " + id + " n'existe pas");
        }

        return commande;
    }

    @PostMapping
    public Commande createCommande(@RequestBody Commande commande) {
        commande.setDate(new Date());
        return commandeRepository.save(commande);
    }

    @PutMapping("/{id}")
    public Commande updateCommande(@PathVariable Long id, @RequestBody Commande commandeDetails) {
        Commande commande = commandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Commande not found"));
        commande.setDescription(commandeDetails.getDescription());
        commande.setQuantite(commandeDetails.getQuantite());
        commande.setMontant(commandeDetails.getMontant());
        return commandeRepository.save(commande);
    }

    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        commandeRepository.deleteById(id);
    }

    @Override
    public Health health() {
        logger.info("****** Actuator : CommandeController health() ");
        long count = commandeRepository.count();
        if (count > 0) {
            return Health.up().withDetail("message", "Il y a des commandes dans la base de données").build();
        } else {
            return Health.down().withDetail("message", "Aucune commande dans la base de données").build();
        }
    }
}