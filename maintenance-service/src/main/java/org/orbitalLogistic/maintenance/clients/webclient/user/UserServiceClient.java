//package org.orbitalLogistic.maintenance.clients.user;
//
//import org.orbitalLogistic.maintenance.dto.common.UserDTO;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Component
//public class UserServiceClient {
//
//    private final WebClient webClient;
//    private final UserServiceClientFallback fallback;
//
//    public UserServiceClient(WebClient.Builder webClientBuilder, UserServiceClientFallback fallback) {
//        this.webClient = webClientBuilder
//                .baseUrl("http://user-service/api/users")
//                .build();
//        this.fallback = fallback;
//    }
//
//    public Mono<UserDTO> getUserById(Long id) {
//        return webClient.get()
//                .uri("/{id}", id)
//                .retrieve()
//                .bodyToMono(UserDTO.class)
//                .onErrorResume(ex -> fallback.getUserById(id, ex));
//    }
//
//    public Mono<Boolean> userExists(Long id) {
//        return webClient.get()
//                .uri("/{id}/exists", id)
//                .retrieve()
//                .bodyToMono(Boolean.class)
//                .onErrorResume(ex -> fallback.userExists(id, ex));
//    }
//}
