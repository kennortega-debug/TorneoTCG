package com.example.TorneoTCG.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.TorneoTCG.client.RecintoClient;
import com.example.TorneoTCG.dto.TorneoDTO;
import com.example.TorneoTCG.dtoexterno.RecintoExternoDTO;
import com.example.TorneoTCG.model.Torneo;
import com.example.TorneoTCG.repository.TorneoRepository;

@ExtendWith(MockitoExtension.class)
class TorneoServiceTest {

    @Mock
    private TorneoRepository torneoRepository;

    @Mock
    private RecintoClient recintoClient;

    @InjectMocks
    private TorneoService torneoService;

    @Test
    void guardar_debeValidarRecintoYConvertirTorneo() {
        Torneo torneo = crearTorneo();
        when(recintoClient.obtenerRecinto(7L)).thenReturn(new RecintoExternoDTO(7L, "Arena TCG"));
        when(torneoRepository.save(torneo)).thenReturn(torneo);

        TorneoDTO resultado = torneoService.guardar(torneo);

        assertEquals("Torneo de prueba", resultado.getNombre());
        assertEquals(7L, resultado.getIdRecinto());
        verify(recintoClient).obtenerRecinto(7L);
        verify(torneoRepository).save(torneo);
    }

    @Test
    void guardar_debeRechazarRecintoInexistente() {
        Torneo torneo = crearTorneo();
        when(recintoClient.obtenerRecinto(7L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> torneoService.guardar(torneo));
        verify(torneoRepository, never()).save(torneo);
    }

    @Test
    void eliminar_debeEliminarCuandoExiste() {
        when(torneoRepository.existsById(5L)).thenReturn(true);

        String resultado = torneoService.eliminar(5L);

        assertEquals("Torneo eliminado correctamente", resultado);
        verify(torneoRepository).deleteById(5L);
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(torneoRepository.findById(80L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> torneoService.buscarPorId(80L));
    }

    private Torneo crearTorneo() {
        Torneo torneo = new Torneo();
        torneo.setNombre("Torneo de prueba");
        torneo.setFechaInicio(LocalDate.of(2026, 11, 1));
        torneo.setFechaFin(LocalDate.of(2026, 11, 2));
        torneo.setEstado("Programado");
        torneo.setIdRecinto(7L);
        return torneo;
    }
}
