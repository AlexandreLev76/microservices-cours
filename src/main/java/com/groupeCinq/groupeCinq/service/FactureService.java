package com.groupeCinq.groupeCinq.service;

import com.groupeCinq.groupeCinq.model.Facture;
import com.groupeCinq.groupeCinq.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FactureService {
    
    @Autowired
    private FactureRepository factureRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Value("${notification.email:alexandre.levenez1@gmail.com}")
    private String notificationEmail;
    
    public Facture create(Facture facture) {
        // Vérifie les informations
        if (!isValid(facture)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informations invalides");
        }
        
        // Enregistre la facture en BDD
        Facture savedFacture = factureRepository.save(facture);
        
        // Envoie une notification avec la facture
        notificationService.createNotification(
                facture.getDestinataire(),
                "Facture : " + facture.getTitre(),
                "Bonjour,\n\n" +
                "Une nouvelle facture a été créée.\n\n" +
                "Titre : " + facture.getTitre() + "\n" +
                "Émetteur : " + facture.getEmetteur() + "\n" +
                "Montant HT : " + facture.getMontantHT() + " €\n" +
                "TVA : " + facture.getTva() + "%\n" +
                "Montant TTC : " + facture.getMontantTTC() + " €\n\n" +
                "Corps :\n" + facture.getCorps()
        );
        
        return savedFacture;
    }
    
    private boolean isValid(Facture facture) {
        return facture.getTitre() != null && !facture.getTitre().isEmpty() &&
               facture.getDestinataire() != null && !facture.getDestinataire().isEmpty() &&
               facture.getEmetteur() != null && !facture.getEmetteur().isEmpty() &&
               facture.getCorps() != null && !facture.getCorps().isEmpty() &&
               facture.getMontantHT() != null && facture.getMontantHT() > 0 &&
               facture.getTva() != null && facture.getTva() >= 0;
    }
    
    public Facture findById(Long id) {
        return factureRepository.findById(id).orElse(null);
    }
    
    public List<Facture> findAll() {
        return factureRepository.findAll();
    }
    
    public Facture update(Long id, Facture facture) {
        return factureRepository.findById(id).map(existingFacture -> {
            if (!isValid(facture)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informations invalides");
            }
            existingFacture.setTitre(facture.getTitre());
            existingFacture.setDestinataire(facture.getDestinataire());
            existingFacture.setEmetteur(facture.getEmetteur());
            existingFacture.setCorps(facture.getCorps());
            existingFacture.setMontantHT(facture.getMontantHT());
            existingFacture.setTva(facture.getTva());
            return factureRepository.save(existingFacture);
        }).orElse(null);
    }
    
    public void delete(Long id) {
        factureRepository.deleteById(id);
    }
}

