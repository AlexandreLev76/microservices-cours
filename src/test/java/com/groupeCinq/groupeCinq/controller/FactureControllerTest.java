package com.groupeCinq.groupeCinq.controller;

import com.groupeCinq.groupeCinq.model.Facture;
import com.groupeCinq.groupeCinq.service.FactureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FactureControllerTest {

    @Mock
    private FactureService factureService;

    @InjectMocks
    private FactureController factureController;

    private Facture factureValide;

    @BeforeEach
    void setUp() {
        factureValide = new Facture("Facture Test", "client@mail.com", "fournisseur", "Corps", 100.0, 20.0);
    }

    @Test
    void testCreateRetourneStatus200AvecLaFacture() {
        when(factureService.create(any(Facture.class))).thenReturn(factureValide);

        ResponseEntity<Facture> response = factureController.create(factureValide);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(factureValide);
        assertThat(response.getBody().getMontantTTC()).isEqualTo(120.0);
    }

    @Test
    void testCreatePropagelExceptionSiFactureInvalide() {
        when(factureService.create(any(Facture.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informations invalides"));

        assertThatThrownBy(() -> factureController.create(new Facture()))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testFindByIdRetourne200SiFactureTrouvee() {
        when(factureService.findById(1L)).thenReturn(factureValide);

        ResponseEntity<Facture> response = factureController.findById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(factureValide);
    }

    @Test
    void testFindByIdRetourne404SiFactureInexistante() {
        when(factureService.findById(999L)).thenReturn(null);

        ResponseEntity<Facture> response = factureController.findById(999L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void testFindAllRetourne200AvecListeDesFactures() {
        Facture f2 = new Facture("Facture 2", "autre@mail.com", "emetteur", "corps", 200.0, 10.0);
        when(factureService.findAll()).thenReturn(List.of(factureValide, f2));

        ResponseEntity<List<Facture>> response = factureController.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void testUpdateRetourne200SiFactureMiseAJour() {
        Facture updated = new Facture("Nouveau titre", "client@mail.com", "fournisseur", "Corps", 200.0, 10.0);
        when(factureService.update(eq(1L), any(Facture.class))).thenReturn(updated);

        ResponseEntity<Facture> response = factureController.update(1L, updated);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitre()).isEqualTo("Nouveau titre");
    }

    @Test
    void testUpdateRetourne404SiFactureInexistante() {
        when(factureService.update(eq(999L), any(Facture.class))).thenReturn(null);

        ResponseEntity<Facture> response = factureController.update(999L, factureValide);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteRetourne204NoContent() {
        doNothing().when(factureService).delete(1L);

        ResponseEntity<Void> response = factureController.delete(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(factureService).delete(1L);
    }
}
