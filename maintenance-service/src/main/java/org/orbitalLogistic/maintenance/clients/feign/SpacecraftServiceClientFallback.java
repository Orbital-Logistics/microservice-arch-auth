//package org.orbitalLogistic.maintenance.clients.feign;
//
//import lombok.extern.slf4j.Slf4j;
//import org.orbitalLogistic.maintenance.dto.common.SpacecraftDTO;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//@Slf4j
//@Component
//public class SpacecraftServiceClientFallback implements SpacecraftServiceFeignClient {
//
//    @Override
//    public Mono<SpacecraftDTO> getSpacecraftById(Long id) {
//        log.warn("Fallback: Unable to fetch spacecraft with id: {}", id);
//        return Mono.just("Unknown");
//    }
//
//    @Override
//    public Boolean spacecraftExists(Long id) {
//        log.warn("Fallback: Unable to check if spacecraft exists with id: {}", id);
//        return false;
//    }
//}
