package com.groupeCinq.groupeCinq.controller;

import com.groupeCinq.groupeCinq.model.Facture;
import com.groupeCinq.groupeCinq.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factures")
public class FactureController {
    
    @Autowired
    private FactureService factureService;
    
    @PostMapping
    public ResponseEntity<Facture> create(@RequestBody Facture facture) {
        Facture created = factureService.create(facture);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Facture> findById(@PathVariable Long id) {
        Facture facture = factureService.findById(id);
        if (facture != null) {
            return ResponseEntity.ok(facture);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping
    public ResponseEntity<List<Facture>> findAll() {
        return ResponseEntity.ok(factureService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Facture> update(@PathVariable Long id, @RequestBody Facture facture) {
        Facture updated = factureService.update(id, facture);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        factureService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

