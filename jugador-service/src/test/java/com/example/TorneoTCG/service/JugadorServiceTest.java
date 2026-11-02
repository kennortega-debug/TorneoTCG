package com.example.TorneoTCG.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.TorneoTCG.dto.JugadorDTO;
import com.example.TorneoTCG.model.Jugador;
import com.example.TorneoTCG.model.Mazo;
import com.example.TorneoTCG.repository.JugadorRepository;

@ExtendWith(MockitoExtension.class)
class JugadorServiceTest {

    @Mock
    private JugadorRepository jugadorRepository;

    @InjectMocks
    private JugadorService jugadorService;

    @Test
    void obtenerTodos_debeConvertirJugadorYNombreDeMazos() {
        Jugador jugador = Jugador.builder()
                .id(12)
                .nombre("Ana")
                .email("ana@tcg.cl")
                .mazos(List.of(Mazo.builder().nombre("Commander Azul").build()))
                .build();
        when(jugadorRepository.findAll()).thenReturn(List.of(jugador));

        List<JugadorDTO> resultado = jugadorService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.get(0).getNombre());
        assertEquals("ana@tcg.cl", resultado.get(0).getEmail());
        assertEquals(List.of("Commander Azul"), resultado.get(0).getNombreMazos());
        verify(jugadorRepository).findAll();
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(jugadorRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> jugadorService.buscarPorId(99));
    }

    @Test
    void guardar_debeDelegarEnRepositorio() {
        Jugador jugador = Jugador.builder().nombre("Luis").email("luis@tcg.cl").build();
        when(jugadorRepository.save(jugador)).thenReturn(jugador);

        assertSame(jugador, jugadorService.guardar(jugador));
        verify(jugadorRepository).save(jugador);
    }
}
