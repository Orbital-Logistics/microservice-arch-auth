//package org.orbitalLogistic.maintenance.clients.spacecraft;
//
//import lombok.extern.slf4j.Slf4j;
//import org.orbitalLogistic.maintenance.dto.common.SpacecraftDTO;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//@Slf4j
//@Component
//public class SpacecraftServiceClientFallback {
//
//    public Mono<SpacecraftDTO> getSpacecraftById(Long id, Throwable error) {
//        log.warn("Fallback triggered for spacecraft id: {}. Error: {}", id, error.getMessage(), error);
//        return Mono.empty();
//    }
//
//    public Mono<Boolean> spacecraftExists(Long id, Throwable error) {
//        log.warn("Fallback: Unable to check if spacecraft exists with id: {}. Error: {}", id, error.getMessage(), error);
//        return Mono.just(false);
//    }
//}
