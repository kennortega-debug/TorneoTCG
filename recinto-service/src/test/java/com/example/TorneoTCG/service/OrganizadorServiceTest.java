package com.example.TorneoTCG.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.example.TorneoTCG.dto.OrganizadorDTO;
import com.example.TorneoTCG.model.Organizador;
import com.example.TorneoTCG.repository.OrganizadorRepository;

@ExtendWith(MockitoExtension.class)
class OrganizadorServiceTest {

    @Mock
    private OrganizadorRepository organizadorRepository;

    @InjectMocks
    private OrganizadorService organizadorService;

    @Test
    void obtenerTodos_debeRetornarListaConvertida() {
        Organizador organizador = new Organizador();
        organizador.setId(1L);
        organizador.setNombre("Ana");
        organizador.setApellido("Pérez");
        organizador.setEmail("ana@example.com");
        organizador.setTelefono("987654321");
        organizador.setCargo("Coordinadora");

        when(organizadorRepository.findAll()).thenReturn(List.of(organizador));

        List<OrganizadorDTO> resultado = organizadorService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.get(0).getNombre());
        assertEquals("Pérez", resultado.get(0).getApellido());
        verify(organizadorRepository).findAll();
    }

    @Test
    void obtenerPorId_debeRetornarOrganizadorCuandoExiste() {
        Organizador organizador = new Organizador();
        organizador.setId(2L);
        organizador.setNombre("Luis");
        organizador.setEmail("luis@example.com");

        when(organizadorRepository.findById(2L)).thenReturn(Optional.of(organizador));

        OrganizadorDTO resultado = organizadorService.obtenerPorId(2L);

        assertEquals(2L, resultado.getId());
        assertEquals("Luis", resultado.getNombre());
        assertEquals("luis@example.com", resultado.getEmail());
    }

    @Test
    void guardar_debePersistirYConvertirOrganizador() {
        Organizador nuevo = new Organizador();
        nuevo.setNombre("Clara");

        Organizador guardado = new Organizador();
        guardado.setId(3L);
        guardado.setNombre("Clara");

        when(organizadorRepository.save(nuevo)).thenReturn(guardado);

        OrganizadorDTO resultado = organizadorService.Guardar(nuevo);

        assertEquals(3L, resultado.getId());
        assertEquals("Clara", resultado.getNombre());
    }

    @Test
    void actualizar_debeModificarCamposYGuardarCambios() {
        Organizador existente = new Organizador();
        existente.setId(4L);
        existente.setNombre("Antiguo");
        existente.setEmail("old@example.com");
        existente.setTelefono("1111111");
        existente.setApellido("Soto");
        existente.setCargo("Voluntario");

        OrganizadorDTO dto = new OrganizadorDTO();
        dto.setNombre("Nuevo");
        dto.setEmail("new@example.com");
        dto.setTelefono("2222222");
        dto.setApellido("Rojas");
        dto.setCargo("Jefe de mesa");

        when(organizadorRepository.findById(4L)).thenReturn(Optional.of(existente));
        when(organizadorRepository.save(existente)).thenReturn(existente);

        OrganizadorDTO resultado = organizadorService.actualizar(4L, dto);

        assertEquals("Nuevo", resultado.getNombre());
        assertEquals("new@example.com", resultado.getEmail());
        assertEquals("Jefe de mesa", resultado.getCargo());
        verify(organizadorRepository).save(existente);
    }

    @Test
    void obtenerPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(organizadorRepository.findById(8L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> organizadorService.obtenerPorId(8L));

        assertEquals("Organizador no encontrado", exception.getMessage());
    }
}
