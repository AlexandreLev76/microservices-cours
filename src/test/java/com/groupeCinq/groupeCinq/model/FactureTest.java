package com.groupeCinq.groupeCinq.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class FactureTest {

    @Test
    void testMontantTTCCalculeAvec20PourcentTVA() {
        Facture facture = new Facture("Titre", "dest@mail.com", "emetteur", "corps", 100.0, 20.0);

        assertThat(facture.getMontantTTC()).isEqualTo(120.0);
    }

    @Test
    void testMontantTTCCalculeAvec0PourcentTVA() {
        Facture facture = new Facture("Titre", "dest@mail.com", "emetteur", "corps", 100.0, 0.0);

        assertThat(facture.getMontantTTC()).isEqualTo(100.0);
    }

    @Test
    void testMontantTTCCalculeAvecTVADecimale() {
        Facture facture = new Facture("Titre", "dest@mail.com", "emetteur", "corps", 200.0, 5.5);

        assertThat(facture.getMontantTTC()).isEqualTo(211.0);
    }

    @Test
    void testSetMontantHTRecalculeMontantTTC() {
        Facture facture = new Facture("Titre", "dest@mail.com", "emetteur", "corps", 100.0, 20.0);

        facture.setMontantHT(200.0);

        assertThat(facture.getMontantTTC()).isEqualTo(240.0);
    }

    @Test
    void testSetTvaRecalculeMontantTTC() {
        Facture facture = new Facture("Titre", "dest@mail.com", "emetteur", "corps", 100.0, 20.0);

        facture.setTva(10.0);

        // Tolérance nécessaire pour les imprécisions de virgule flottante (100 * 1.1 = 110.00000000000001)
        assertThat(facture.getMontantTTC()).isCloseTo(110.0, within(0.001));
    }

    @Test
    void testGettersRetournentLesValeursCorrectement() {
        Facture facture = new Facture("Facture 1", "client@mail.com", "fournisseur", "Description", 500.0, 5.5);

        assertThat(facture.getTitre()).isEqualTo("Facture 1");
        assertThat(facture.getDestinataire()).isEqualTo("client@mail.com");
        assertThat(facture.getEmetteur()).isEqualTo("fournisseur");
        assertThat(facture.getCorps()).isEqualTo("Description");
        assertThat(facture.getMontantHT()).isEqualTo(500.0);
        assertThat(facture.getTva()).isEqualTo(5.5);
    }

    @Test
    void testSettersModifientLesValeursCorrectement() {
        Facture facture = new Facture();
        facture.setTitre("Nouveau Titre");
        facture.setDestinataire("nouveau@mail.com");
        facture.setEmetteur("NouvelEmetteur");
        facture.setCorps("Nouveau corps");

        assertThat(facture.getTitre()).isEqualTo("Nouveau Titre");
        assertThat(facture.getDestinataire()).isEqualTo("nouveau@mail.com");
        assertThat(facture.getEmetteur()).isEqualTo("NouvelEmetteur");
        assertThat(facture.getCorps()).isEqualTo("Nouveau corps");
    }

    @Test
    void testIdFactureEstNullAvantPersistance() {
        Facture facture = new Facture("Titre", "dest@mail.com", "emetteur", "corps", 100.0, 20.0);

        assertThat(facture.getIdFacture()).isNull();
    }
}
