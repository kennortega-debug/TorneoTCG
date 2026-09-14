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

import com.example.TorneoTCG.dto.ComunaDTO;
import com.example.TorneoTCG.dto.RegionDTO;
import com.example.TorneoTCG.model.Comuna;
import com.example.TorneoTCG.model.Region;
import com.example.TorneoTCG.repository.ComunaRepository;
import com.example.TorneoTCG.repository.RegionRepository;

@ExtendWith(MockitoExtension.class)
class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private ComunaService comunaService;

    @Test
    void obtenerTodos_debeRetornarListaConvertida() {
        Region region = new Region();
        region.setId(1L);
        region.setNombre("Metropolitana");

        Comuna comuna = new Comuna();
        comuna.setId(10L);
        comuna.setNombre("Providencia");
        comuna.setRegion(region);

        when(comunaRepository.findAll()).thenReturn(List.of(comuna));

        List<ComunaDTO> resultado = comunaService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getId());
        assertEquals("Providencia", resultado.get(0).getNombre());
        assertEquals("Metropolitana", resultado.get(0).getRegion().getNombre());
        verify(comunaRepository).findAll();
    }

    @Test
    void obtenerPorId_debeRetornarComunaCuandoExiste() {
        Region region = new Region();
        region.setId(3L);
        region.setNombre("Valparaíso");

        Comuna comuna = new Comuna();
        comuna.setId(7L);
        comuna.setNombre("Viña del Mar");
        comuna.setRegion(region);

        when(comunaRepository.findById(7L)).thenReturn(Optional.of(comuna));

        ComunaDTO resultado = comunaService.obtenerPorId(7L);

        assertEquals(7L, resultado.getId());
        assertEquals("Viña del Mar", resultado.getNombre());
        assertEquals("Valparaíso", resultado.getRegion().getNombre());
    }

    @Test
    void obtenerPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(comunaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> comunaService.obtenerPorId(99L));

        assertEquals("Comuna no encontrada", exception.getMessage());
    }

    @Test
    void guardar_debePersistirYConvertirComuna() {
        Comuna nuevaComuna = new Comuna();
        nuevaComuna.setNombre("Las Condes");

        Comuna comunaGuardada = new Comuna();
        comunaGuardada.setId(20L);
        comunaGuardada.setNombre("Las Condes");
        Region region = new Region();
        region.setId(5L);
        region.setNombre("Metropolitana");
        comunaGuardada.setRegion(region);

        when(comunaRepository.save(nuevaComuna)).thenReturn(comunaGuardada);

        ComunaDTO resultado = comunaService.Guardar(nuevaComuna);

        assertEquals(20L, resultado.getId());
        assertEquals("Las Condes", resultado.getNombre());
        verify(comunaRepository).save(nuevaComuna);
    }

    @Test
    void actualizar_debeActualizarComunaYRegion() {
        Region regionAnterior = new Region();
        regionAnterior.setId(1L);
        regionAnterior.setNombre("Anterior");

        Region regionNueva = new Region();
        regionNueva.setId(2L);
        regionNueva.setNombre("Nueva");

        Comuna comunaExistente = new Comuna();
        comunaExistente.setId(5L);
        comunaExistente.setNombre("Viejo nombre");
        comunaExistente.setRegion(regionAnterior);

        ComunaDTO dto = new ComunaDTO();
        dto.setNombre("Nuevo nombre");
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(2L);
        dto.setRegion(regionDTO);

        when(comunaRepository.findById(5L)).thenReturn(Optional.of(comunaExistente));
        when(regionRepository.findById(2L)).thenReturn(Optional.of(regionNueva));
        when(comunaRepository.save(comunaExistente)).thenReturn(comunaExistente);

        ComunaDTO resultado = comunaService.actualizar(5L, dto);

        assertEquals("Nuevo nombre", resultado.getNombre());
        assertEquals("Nueva", resultado.getRegion().getNombre());
        verify(comunaRepository).save(comunaExistente);
    }

    @Test
    void actualizar_debeLanzarExcepcionCuandoRegionNoExiste() {
        Comuna comunaExistente = new Comuna();
        comunaExistente.setId(5L);
        comunaExistente.setNombre("Viejo nombre");

        ComunaDTO dto = new ComunaDTO();
        dto.setNombre("Nuevo nombre");
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(99L);
        dto.setRegion(regionDTO);

        when(comunaRepository.findById(5L)).thenReturn(Optional.of(comunaExistente));
        when(regionRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> comunaService.actualizar(5L, dto));

        assertTrue(exception.getMessage().contains("Error al actualizar la comuna"));
        assertTrue(exception.getMessage().contains("Región no encontrada"));
    }

    @Test
    void eliminar_debeEliminarYRetornarMensaje() {
        Comuna comuna = new Comuna();
        comuna.setId(3L);
        comuna.setNombre("Estación Central");

        when(comunaRepository.findById(3L)).thenReturn(Optional.of(comuna));

        String resultado = comunaService.eliminar(3L);

        assertTrue(resultado.contains("Estación Central"));
        assertTrue(resultado.contains("eliminada exitosamente"));
        verify(comunaRepository).delete(comuna);
    }
}
