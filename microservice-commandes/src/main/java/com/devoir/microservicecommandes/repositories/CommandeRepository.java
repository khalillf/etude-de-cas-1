package com.devoir.microservicecommandes.repositories;

import com.devoir.microservicecommandes.models.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByDateAfter(Date date);
}
