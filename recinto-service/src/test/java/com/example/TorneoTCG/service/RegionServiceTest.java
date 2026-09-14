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

import com.example.TorneoTCG.dto.RegionDTO;
import com.example.TorneoTCG.model.Region;
import com.example.TorneoTCG.repository.RegionRepository;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    @Test
    void obtenerTodos_debeRetornarRegionesConvertidas() {
        Region region = new Region();
        region.setId(1L);
        region.setNombre("Arica");

        when(regionRepository.findAll()).thenReturn(List.of(region));

        List<RegionDTO> resultado = regionService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Arica", resultado.get(0).getNombre());
        verify(regionRepository).findAll();
    }

    @Test
    void obtenerPorId_debeRetornarRegionCuandoExiste() {
        Region region = new Region();
        region.setId(2L);
        region.setNombre("Tarapacá");

        when(regionRepository.findById(2L)).thenReturn(Optional.of(region));

        RegionDTO resultado = regionService.obtenerPorId(2L);

        assertEquals(2L, resultado.getId());
        assertEquals("Tarapacá", resultado.getNombre());
    }

    @Test
    void guardar_debePersistirYConvertirRegion() {
        Region nuevaRegion = new Region();
        nuevaRegion.setNombre("Biobío");

        Region regionGuardada = new Region();
        regionGuardada.setId(3L);
        regionGuardada.setNombre("Biobío");

        when(regionRepository.save(nuevaRegion)).thenReturn(regionGuardada);

        RegionDTO resultado = regionService.Guardar(nuevaRegion);

        assertEquals(3L, resultado.getId());
        assertEquals("Biobío", resultado.getNombre());
    }

    @Test
    void actualizar_debeModificarNombreYGuardarCambios() {
        Region regionExistente = new Region();
        regionExistente.setId(4L);
        regionExistente.setNombre("Antigua");

        RegionDTO dto = new RegionDTO();
        dto.setNombre("Actualizada");

        when(regionRepository.findById(4L)).thenReturn(Optional.of(regionExistente));
        when(regionRepository.save(regionExistente)).thenReturn(regionExistente);

        RegionDTO resultado = regionService.actualizar(4L, dto);

        assertEquals("Actualizada", resultado.getNombre());
        verify(regionRepository).save(regionExistente);
    }

    @Test
    void eliminar_debeEliminarYRetornarMensaje() {
        Region region = new Region();
        region.setId(5L);
        region.setNombre("Los Lagos");

        when(regionRepository.findById(5L)).thenReturn(Optional.of(region));

        String resultado = regionService.eliminar(5L);

        assertTrue(resultado.contains("Los Lagos"));
        assertTrue(resultado.contains("eliminada exitosamente"));
        verify(regionRepository).delete(region);
    }

    @Test
    void obtenerPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(regionRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> regionService.obtenerPorId(10L));

        assertEquals("Región no encontrada", exception.getMessage());
    }
}
