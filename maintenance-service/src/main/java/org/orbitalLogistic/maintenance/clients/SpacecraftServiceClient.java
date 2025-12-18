package org.orbitalLogistic.maintenance.clients;

import lombok.RequiredArgsConstructor;
import org.orbitalLogistic.maintenance.clients.feign.SpacecraftServiceFeignClient;
import org.orbitalLogistic.maintenance.dto.common.SpacecraftDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class SpacecraftServiceClient {
    private final SpacecraftServiceFeignClient spacecraftServiceFeignClient;

    public Mono<SpacecraftDTO> getSpacecraftById(Long id) {
        return Mono.fromCallable(() -> spacecraftServiceFeignClient.getSpacecraftById(id))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Boolean> spacecraftExists(Long id) {
        return Mono.fromCallable(() -> spacecraftServiceFeignClient.spacecraftExists(id))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
