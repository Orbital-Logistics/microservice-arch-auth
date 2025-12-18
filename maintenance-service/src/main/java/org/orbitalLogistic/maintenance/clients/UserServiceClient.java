package org.orbitalLogistic.maintenance.clients;

import lombok.RequiredArgsConstructor;
import org.orbitalLogistic.maintenance.clients.feign.UserServiceFeignClient;
import org.orbitalLogistic.maintenance.dto.common.UserDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class UserServiceClient {
    private final UserServiceFeignClient userServiceFeignClient;

    public Mono<UserDTO> getUserById(Long id) {
        return Mono.fromCallable(() -> userServiceFeignClient.getUserById(id))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Boolean> userExists(Long id) {
        return Mono.fromCallable(() -> userServiceFeignClient.userExists(id))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
