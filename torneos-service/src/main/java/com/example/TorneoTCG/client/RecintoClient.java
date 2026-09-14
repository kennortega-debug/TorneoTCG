package com.example.TorneoTCG.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.TorneoTCG.dtoexterno.RecintoExternoDTO;

@Service
public class RecintoClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public RecintoExternoDTO obtenerRecinto(Long id) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("http://recinto-service/api/v1/recintos/{id}", id)
                    .retrieve()
                    .bodyToMono(RecintoExternoDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            return null;
        }
    }

}
