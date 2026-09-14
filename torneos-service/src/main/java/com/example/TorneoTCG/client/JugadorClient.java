package com.example.TorneoTCG.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.TorneoTCG.dtoexterno.JugadorExternoDTO;

@Service
public class JugadorClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public JugadorExternoDTO obtenerJugador(Integer id) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("http://jugador-service/api/v1/jugadores/{id}", id)
                    .retrieve()
                    .bodyToMono(JugadorExternoDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            return null;
        }
    }

}
