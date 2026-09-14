package com.example.TorneoTCG.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.dto.ResultadoDTO;
import com.example.TorneoTCG.model.Partida;
import com.example.TorneoTCG.model.Resultado;
import com.example.TorneoTCG.repository.PartidaRepository;
import com.example.TorneoTCG.repository.ResultadoRepository;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    public List<ResultadoDTO> obtenerTodos() {

        List<ResultadoDTO> listaDTO = new ArrayList<>();

        List<Resultado> resultados = resultadoRepository.findAll();

        for (Resultado resultado : resultados) {
            listaDTO.add(convertirADTO(resultado));
        }

        return listaDTO;
    }

    public ResultadoDTO buscarPorId(Long id) {

        Resultado resultado = resultadoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Resultado no encontrado"));

        return convertirADTO(resultado);
    }

    public ResultadoDTO guardar(Resultado resultado) {

        Resultado guardado = resultadoRepository.save(resultado);

        Partida partida = partidaRepository.findById(guardado.getPartida().getId())
                .orElseThrow(() ->
                        new RuntimeException("Partida no encontrada"));

        partida.setEstado("FINALIZADA");
        partidaRepository.save(partida);

        return convertirADTO(guardado);
    }

    public ResultadoDTO actualizar(Long id, Resultado resultado) {
    Resultado existente = resultadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Resultado no encontrado"));
    existente.setPartida(resultado.getPartida());
    existente.setIdGanador(resultado.getIdGanador());
    existente.setPuntajeJugador1(resultado.getPuntajeJugador1());
    existente.setPuntajeJugador2(resultado.getPuntajeJugador2());
    existente.setPuntajeJugador3(resultado.getPuntajeJugador3());
    existente.setPuntajeJugador4(resultado.getPuntajeJugador4());
    existente.setPuntajeJugador5(resultado.getPuntajeJugador5());
    existente.setPosicionJugador1(resultado.getPosicionJugador1());
    existente.setPosicionJugador2(resultado.getPosicionJugador2());
    existente.setPosicionJugador3(resultado.getPosicionJugador3());
    existente.setPosicionJugador4(resultado.getPosicionJugador4());
    existente.setPosicionJugador5(resultado.getPosicionJugador5());
    Resultado actualizado = resultadoRepository.save(existente);
    return convertirADTO(actualizado);
}

    public String eliminar(Long id) {
    if (resultadoRepository.existsById(id)) {
        resultadoRepository.deleteById(id);
        return "Resultado eliminado correctamente";
    } else {
        return "Resultado no encontrado";
    }
}

    private ResultadoDTO convertirADTO(Resultado resultado) {

        ResultadoDTO dto = new ResultadoDTO();

        dto.setId(resultado.getId());

        dto.setIdPartida(
                resultado.getPartida().getId()
        );

        dto.setIdGanador(
                resultado.getIdGanador()
        );

        dto.setPuntajeJugador1(resultado.getPuntajeJugador1());
        dto.setPuntajeJugador2(resultado.getPuntajeJugador2());
        dto.setPuntajeJugador3(resultado.getPuntajeJugador3());
        dto.setPuntajeJugador4(resultado.getPuntajeJugador4());
        dto.setPuntajeJugador5(resultado.getPuntajeJugador5());

        dto.setPosicionJugador1(resultado.getPosicionJugador1());
        dto.setPosicionJugador2(resultado.getPosicionJugador2());
        dto.setPosicionJugador3(resultado.getPosicionJugador3());
        dto.setPosicionJugador4(resultado.getPosicionJugador4());
        dto.setPosicionJugador5(resultado.getPosicionJugador5());

        return dto;
    }
}