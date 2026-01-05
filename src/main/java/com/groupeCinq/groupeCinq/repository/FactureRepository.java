package com.groupeCinq.groupeCinq.repository;

import com.groupeCinq.groupeCinq.model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
}
