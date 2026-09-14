package com.example.TorneoTCG.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.model.Participacion;
import com.example.TorneoTCG.model.Partida;
import com.example.TorneoTCG.model.Ronda;
import com.example.TorneoTCG.model.Torneo;
import com.example.TorneoTCG.repository.ParticipacionRepository;
import com.example.TorneoTCG.repository.PartidaRepository;
import com.example.TorneoTCG.repository.RondaRepository;
import com.example.TorneoTCG.repository.TorneoRepository;

@Service
public class TorneoLogicaService {

    @Autowired
    private TorneoRepository torneoRepository;

    @Autowired
    private RondaRepository rondaRepository;

    @Autowired
    private ParticipacionRepository participacionRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    public String iniciarTorneo(Long idTorneo) {
        Torneo torneo = torneoRepository.findById(idTorneo)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        List<Participacion> participantes = participacionRepository.findByTorneo(torneo);

        if (participantes.size() < 3) {
            throw new RuntimeException("El torneo necesita mínimo 3 jugadores");
        }

        torneo.setEstado("INICIADO");
        torneoRepository.save(torneo);

        return "Torneo iniciado correctamente";
    }

    public String generarRondas(Long idTorneo) {
        Torneo torneo = torneoRepository.findById(idTorneo)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        List<Participacion> participantes = participacionRepository.findByTorneo(torneo);
        int cantidadParticipantes = participantes.size();

        if (cantidadParticipantes < 3) {
            throw new RuntimeException("El torneo necesita mínimo 3 jugadores para generar rondas");
        }

        int cantidadRondas = calcularCantidadRondas(cantidadParticipantes);
        LocalDate fecha = torneo.getFechaInicio();

        for (int i = 1; i <= cantidadRondas; i++) {
            Ronda ronda = new Ronda();
            ronda.setNumeroRonda(i);
            ronda.setFecha(fecha.plusDays(i - 1));
            ronda.setTorneo(torneo);
            rondaRepository.save(ronda);
        }

        return "Rondas generadas correctamente: " + cantidadRondas;
    }

    public String generarPartidas(Long idRonda) {
        Ronda ronda = rondaRepository.findById(idRonda)
                .orElseThrow(() -> new RuntimeException("Ronda no encontrada"));

        List<Participacion> participantes = participacionRepository.findByTorneo(ronda.getTorneo());

        if (participantes.size() < 3) {
            throw new RuntimeException("La ronda necesita mínimo 3 jugadores para generar partidas");
        }

        List<Integer> jugadores = new ArrayList<>();
        for (Participacion participacion : participantes) {
            jugadores.add(participacion.getIdJugador());
        }

        Collections.shuffle(jugadores);

        int numeroMesa = 1;
        int posicion = 0;

        while (posicion < jugadores.size()) {
            int jugadoresRestantes = jugadores.size() - posicion;
            int cantidadMesa = calcularCantidadMesa(jugadoresRestantes);

            Partida partida = new Partida();
            partida.setMesa("Mesa " + numeroMesa);
            partida.setEstado("PENDIENTE");
            partida.setRonda(ronda);
            partida.setCantidadJugadores(cantidadMesa);
            partida.setIdJugador1(jugadores.get(posicion));
            partida.setIdJugador2(jugadores.get(posicion + 1));
            partida.setIdJugador3(jugadores.get(posicion + 2));

            if (cantidadMesa >= 4) {
                partida.setIdJugador4(jugadores.get(posicion + 3));
            }

            if (cantidadMesa == 5) {
                partida.setIdJugador5(jugadores.get(posicion + 4));
            }

            partidaRepository.save(partida);

            posicion = posicion + cantidadMesa;
            numeroMesa++;
        }

        return "Partidas generadas correctamente";
    }

    public String finalizarTorneo(Long idTorneo) {
        Torneo torneo = torneoRepository.findById(idTorneo)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        torneo.setEstado("FINALIZADO");
        torneoRepository.save(torneo);

        return "Torneo finalizado correctamente";
    }

    private int calcularCantidadRondas(int cantidadParticipantes) {
        if (cantidadParticipantes <= 8) {
            return 3;
        }

        if (cantidadParticipantes <= 16) {
            return 4;
        }

        return 5;
    }

    private int calcularCantidadMesa(int jugadoresRestantes) {
        if (jugadoresRestantes <= 5) {
            if (jugadoresRestantes < 3) {
                throw new RuntimeException("No se puede crear una mesa con menos de 3 jugadores");
            }
            return jugadoresRestantes;
        }

        if (jugadoresRestantes - 5 == 1) {
            return 4;
        }

        if (jugadoresRestantes - 5 == 2) {
            return 3;
        }

        return 5;
    }
}
