package com.example.TorneoTCG.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.TorneoTCG.dto.RecintoDTO;
import com.example.TorneoTCG.model.Comuna;
import com.example.TorneoTCG.model.Recinto;
import com.example.TorneoTCG.model.Region;
import com.example.TorneoTCG.repository.RecintoRepository;

@ExtendWith(MockitoExtension.class)
class RecintoServiceTest {

    @Mock
    private RecintoRepository recintoRepository;

    @InjectMocks
    private RecintoService recintoService;

    @Test
    void obtenerTodos_debeRetornarListaConvertida() {
        Region region = new Region();
        region.setId(1L);
        region.setNombre("Metropolitana");

        Comuna comuna = new Comuna();
        comuna.setId(10L);
        comuna.setNombre("Providencia");
        comuna.setRegion(region);

        Recinto recinto = new Recinto();
        recinto.setId(7L);
        recinto.setNombre("Arena Central");
        recinto.setCapacidad(500);
        recinto.setDireccion("Av. Siempre Viva 123");
        recinto.setComuna(comuna);

        when(recintoRepository.findAll()).thenReturn(List.of(recinto));

        List<RecintoDTO> resultado = recintoService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Arena Central", resultado.get(0).getNombre());
        assertEquals("Providencia", resultado.get(0).getComuna().getNombre());
        verify(recintoRepository).findAll();
    }

    @Test
    void obtenerPorId_debeRetornarRecintoCuandoExiste() {
        Region region = new Region();
        region.setId(1L);
        region.setNombre("Metropolitana");

        Comuna comuna = new Comuna();
        comuna.setId(10L);
        comuna.setNombre("Providencia");
        comuna.setRegion(region);

        Recinto recinto = new Recinto();
        recinto.setId(8L);
        recinto.setNombre("Teatro Municipal");
        recinto.setComuna(comuna);

        when(recintoRepository.findById(8L)).thenReturn(Optional.of(recinto));

        RecintoDTO resultado = recintoService.obtenerPorId(8L);

        assertEquals(8L, resultado.getId());
        assertEquals("Teatro Municipal", resultado.getNombre());
    }

    @Test
    void guardar_debePersistirYConvertirRecinto() {
        Region region = new Region();
        region.setId(2L);
        region.setNombre("Valparaíso");

        Comuna comuna = new Comuna();
        comuna.setId(11L);
        comuna.setNombre("Viña del Mar");
        comuna.setRegion(region);

        Recinto nuevo = new Recinto();
        nuevo.setNombre("Auditorio Norte");
        nuevo.setComuna(comuna);

        Recinto guardado = new Recinto();
        guardado.setId(9L);
        guardado.setNombre("Auditorio Norte");
        guardado.setComuna(comuna);

        when(recintoRepository.save(nuevo)).thenReturn(guardado);

        RecintoDTO resultado = recintoService.Guardar(nuevo);

        assertEquals(9L, resultado.getId());
        assertEquals("Auditorio Norte", resultado.getNombre());
    }

    @Test
    void actualizar_debeActualizarCamposYGuardarCambios() {
        Region region = new Region();
        region.setId(3L);
        region.setNombre("Antofagasta");

        Comuna comuna = new Comuna();
        comuna.setId(12L);
        comuna.setNombre("Centro");
        comuna.setRegion(region);

        Recinto existente = new Recinto();
        existente.setId(4L);
        existente.setNombre("Antiguo");
        existente.setCapacidad(100);
        existente.setDireccion("Calle 1");
        existente.setComuna(comuna);

        RecintoDTO dto = new RecintoDTO();
        dto.setNombre("Nuevo");
        dto.setCapacidad(250);
        dto.setDireccion("Calle 2");

        when(recintoRepository.findById(4L)).thenReturn(Optional.of(existente));
        when(recintoRepository.save(existente)).thenReturn(existente);

        RecintoDTO resultado = recintoService.actualizar(4L, dto);

        assertEquals("Nuevo", resultado.getNombre());
        assertEquals(250, resultado.getCapacidad());
        verify(recintoRepository).save(existente);
    }

    @Test
    void eliminar_debeEliminarYRetornarMensaje() {
        Recinto recinto = new Recinto();
        recinto.setId(5L);
        recinto.setNombre("Pabellón Central");

        when(recintoRepository.findById(5L)).thenReturn(Optional.of(recinto));

        String resultado = recintoService.eliminar(5L);

        assertTrue(resultado.contains("Pabellón Central"));
        assertTrue(resultado.contains("eliminado exitosamente"));
        verify(recintoRepository).delete(recinto);
    }

    @Test
    void obtenerPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(recintoRepository.findById(11L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> recintoService.obtenerPorId(11L));

        assertEquals("Recinto no encontrado", exception.getMessage());
    }
}
