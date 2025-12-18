//package org.orbitalLogistic.maintenance.clients.user;
//
//import lombok.extern.slf4j.Slf4j;
//import org.orbitalLogistic.maintenance.dto.common.UserDTO;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//@Slf4j
//@Component
//public class UserServiceClientFallback {
//
//    public Mono<UserDTO> getUserById(Long id, Throwable error) {
//        log.warn("Fallback: Unable to fetch user with id: {}. Error: {}", id, error.getMessage(), error);
//        return Mono.empty();
//    }
//
//    public Mono<Boolean> userExists(Long id, Throwable error) {
//        log.warn("Fallback: Unable to check if user exists with id: {}. Error: {}", id, error.getMessage(), error);
//        return Mono.just(false);
//    }
//}
