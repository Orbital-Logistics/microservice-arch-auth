package org.orbitalLogistic.maintenance.clients.spacecraft;

import org.orbitalLogistic.maintenance.dto.common.SpacecraftDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SpacecraftServiceClient {

    private final WebClient webClient;
    private final SpacecraftServiceClientFallback fallback;

    public SpacecraftServiceClient(WebClient.Builder webClientBuilder, SpacecraftServiceClientFallback fallback) {
        this.webClient = webClientBuilder
                .baseUrl("http://spacecraft-service/api/spacecrafts")
                .build();
        this.fallback = fallback;
    }

    public Mono<SpacecraftDTO> getSpacecraftById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(SpacecraftDTO.class)
                .onErrorResume(ex -> fallback.getSpacecraftById(id, ex));
    }

    public Mono<Boolean> spacecraftExists(Long id) {
        return webClient.get()
                .uri("/{id}/exists", id)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(ex -> fallback.spacecraftExists(id, ex));
    }
}

