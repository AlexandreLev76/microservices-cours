package com.groupeCinq.groupeCinq.model;

import jakarta.persistence.*;

@Entity
@Table(name = "factures")
public class Facture {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacture;
    
    @Column(nullable = false)
    private String titre;
    
    @Column(nullable = false)
    private String destinataire;
    
    @Column(nullable = false)
    private String emetteur;
    
    @Column(columnDefinition = "TEXT")
    private String corps;
    
    @Column(nullable = false)
    private Double montantHT;
    
    @Column(nullable = false)
    private Double tva;
    
    @Column(nullable = false)
    private Double montantTTC;
    
    public Facture() {
    }
    
    public Facture(String titre, String destinataire, String emetteur, String corps, Double montantHT, Double tva) {
        this.titre = titre;
        this.destinataire = destinataire;
        this.emetteur = emetteur;
        this.corps = corps;
        this.montantHT = montantHT;
        this.tva = tva;
        this.montantTTC = montantHT * (1 + tva / 100);
    }
    
    @PrePersist
    @PreUpdate
    private void calculateMontantTTC() {
        if (montantHT != null && tva != null) {
            this.montantTTC = this.montantHT * (1 + this.tva / 100);
        }
    }
    
    public Long getIdFacture() {
        return idFacture;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getDestinataire() {
        return destinataire;
    }
    
    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }
    
    public String getEmetteur() {
        return emetteur;
    }
    
    public void setEmetteur(String emetteur) {
        this.emetteur = emetteur;
    }
    
    public String getCorps() {
        return corps;
    }
    
    public void setCorps(String corps) {
        this.corps = corps;
    }
    
    public Double getMontantHT() {
        return montantHT;
    }
    
    public void setMontantHT(Double montantHT) {
        this.montantHT = montantHT;
        calculateMontantTTC();
    }
    
    public Double getTva() {
        return tva;
    }
    
    public void setTva(Double tva) {
        this.tva = tva;
        calculateMontantTTC();
    }
    
    public Double getMontantTTC() {
        return montantTTC;
    }
}
