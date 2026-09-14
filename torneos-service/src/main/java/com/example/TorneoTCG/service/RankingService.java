package com.example.TorneoTCG.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.dto.RankingDTO;
import com.example.TorneoTCG.model.Partida;
import com.example.TorneoTCG.model.Resultado;
import com.example.TorneoTCG.model.Ronda;
import com.example.TorneoTCG.model.Torneo;
import com.example.TorneoTCG.repository.PartidaRepository;
import com.example.TorneoTCG.repository.ResultadoRepository;
import com.example.TorneoTCG.repository.RondaRepository;
import com.example.TorneoTCG.repository.TorneoRepository;

@Service
public class RankingService {

    @Autowired
    private TorneoRepository torneoRepository;

    @Autowired
    private RondaRepository rondaRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ResultadoRepository resultadoRepository;

    public List<RankingDTO> obtenerRankingPorTorneo(Long idTorneo) {
        Torneo torneo = torneoRepository.findById(idTorneo)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        Map<Integer, RankingDTO> ranking = new HashMap<>();
        List<Ronda> rondas = rondaRepository.findByTorneo(torneo);

        for (Ronda ronda : rondas) {
            List<Partida> partidas = partidaRepository.findByRonda(ronda);

            for (Partida partida : partidas) {
                Resultado resultado = resultadoRepository.findByPartida(partida);

                if (resultado != null) {
                    sumarJugador(ranking, partida.getIdJugador1(), resultado.getPosicionJugador1(), resultado.getIdGanador());
                    sumarJugador(ranking, partida.getIdJugador2(), resultado.getPosicionJugador2(), resultado.getIdGanador());
                    sumarJugador(ranking, partida.getIdJugador3(), resultado.getPosicionJugador3(), resultado.getIdGanador());
                    sumarJugador(ranking, partida.getIdJugador4(), resultado.getPosicionJugador4(), resultado.getIdGanador());
                    sumarJugador(ranking, partida.getIdJugador5(), resultado.getPosicionJugador5(), resultado.getIdGanador());
                }
            }
        }

        List<RankingDTO> lista = new ArrayList<>(ranking.values());
        lista.sort(Comparator.comparing(RankingDTO::getPuntajeTotal).reversed());
        return lista;
    }

    private void sumarJugador(Map<Integer, RankingDTO> ranking, Integer idJugador, Integer posicion, Integer idGanador) {
        if (idJugador == null) {
            return;
        }

        RankingDTO dto = obtenerRankingJugador(ranking, idJugador);
        dto.setPuntajeTotal(dto.getPuntajeTotal() + calcularPuntos(posicion));
        dto.setPartidasJugadas(dto.getPartidasJugadas() + 1);

        if (idJugador.equals(idGanador)) {
            dto.setVictorias(dto.getVictorias() + 1);
        }
    }

    private RankingDTO obtenerRankingJugador(Map<Integer, RankingDTO> ranking, Integer idJugador) {
        if (ranking.containsKey(idJugador)) {
            return ranking.get(idJugador);
        }

        RankingDTO dto = new RankingDTO();
        dto.setIdJugador(idJugador);
        dto.setNombreJugador(obtenerNombreJugador(idJugador));
        dto.setPuntajeTotal(0);
        dto.setPartidasJugadas(0);
        dto.setVictorias(0);
        ranking.put(idJugador, dto);

        return dto;
    }

    private String obtenerNombreJugador(Integer idJugador) {
        return "Jugador " + idJugador;
    }

    private int calcularPuntos(Integer posicion) {
        if (posicion == null) {
            return 0;
        }

        if (posicion == 1) {
            return 5;
        }

        if (posicion == 2) {
            return 3;
        }

        if (posicion == 3) {
            return 2;
        }

        if (posicion == 4) {
            return 1;
        }

        return 0;
    }
}
