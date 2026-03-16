package com.groupeCinq.groupeCinq.service;

import com.groupeCinq.groupeCinq.model.Facture;
import com.groupeCinq.groupeCinq.repository.FactureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FactureServiceTest {

    @Mock
    private FactureRepository factureRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private FactureService factureService;

    private Facture factureValide;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(factureService, "notificationEmail", "test@example.com");

        factureValide = new Facture("Facture Test", "client@mail.com", "fournisseur", "Corps de la facture", 100.0, 20.0);
    }

    @Test
    void testCreateSauvegardeEtEnvoieUneNotification() {
        when(factureRepository.save(factureValide)).thenReturn(factureValide);

        Facture result = factureService.create(factureValide);

        assertThat(result).isEqualTo(factureValide);
        verify(factureRepository).save(factureValide);
        verify(notificationService).createNotification(
                eq("client@mail.com"),
                anyString(),
                anyString()
        );
    }

    @Test
    void testCreateLanceExceptionSiTitreEstNul() {
        Facture facture = new Facture(null, "client@mail.com", "emetteur", "corps", 100.0, 20.0);

        assertThatThrownBy(() -> factureService.create(facture))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateLanceExceptionSiTitreEstVide() {
        Facture facture = new Facture("", "client@mail.com", "emetteur", "corps", 100.0, 20.0);

        assertThatThrownBy(() -> factureService.create(facture))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateLanceExceptionSiDestinataireEstNul() {
        Facture facture = new Facture("Titre", null, "emetteur", "corps", 100.0, 20.0);

        assertThatThrownBy(() -> factureService.create(facture))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateLanceExceptionSiMontantHTEstZero() {
        Facture facture = new Facture("Titre", "client@mail.com", "emetteur", "corps", 0.0, 20.0);

        assertThatThrownBy(() -> factureService.create(facture))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateLanceExceptionSiMontantHTEstNegatif() {
        Facture facture = new Facture("Titre", "client@mail.com", "emetteur", "corps", -50.0, 20.0);

        assertThatThrownBy(() -> factureService.create(facture))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateAccepteTvaEgaleAZero() {
        Facture facture = new Facture("Titre", "client@mail.com", "emetteur", "corps", 100.0, 0.0);
        when(factureRepository.save(facture)).thenReturn(facture);

        Facture result = factureService.create(facture);

        assertThat(result).isEqualTo(facture);
    }

    @Test
    void testFindByIdRetourneLaFacture() {
        when(factureRepository.findById(1L)).thenReturn(Optional.of(factureValide));

        Facture result = factureService.findById(1L);

        assertThat(result).isEqualTo(factureValide);
    }

    @Test
    void testFindByIdRetourneNullSiInexistante() {
        when(factureRepository.findById(999L)).thenReturn(Optional.empty());

        Facture result = factureService.findById(999L);

        assertThat(result).isNull();
    }

    @Test
    void testFindAllRetourneToutesLesFactures() {
        Facture f2 = new Facture("Facture 2", "autre@mail.com", "emetteur", "corps", 200.0, 10.0);
        when(factureRepository.findAll()).thenReturn(List.of(factureValide, f2));

        List<Facture> result = factureService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void testUpdateModifieLaFactureSiElleExiste() {
        Facture existing = new Facture("Ancien titre", "client@mail.com", "emetteur", "ancien corps", 100.0, 20.0);
        Facture updated = new Facture("Nouveau titre", "client@mail.com", "emetteur", "nouveau corps", 200.0, 10.0);

        when(factureRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(factureRepository.save(existing)).thenReturn(existing);

        Facture result = factureService.update(1L, updated);

        assertThat(result.getTitre()).isEqualTo("Nouveau titre");
        assertThat(result.getMontantHT()).isEqualTo(200.0);
    }

    @Test
    void testUpdateRetourneNullSiFactureInexistante() {
        when(factureRepository.findById(999L)).thenReturn(Optional.empty());

        Facture result = factureService.update(999L, factureValide);

        assertThat(result).isNull();
    }

    @Test
    void testDeleteAppelleLeRepository() {
        factureService.delete(1L);

        verify(factureRepository).deleteById(1L);
    }
}
