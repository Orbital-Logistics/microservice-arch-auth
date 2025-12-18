package org.orbitalLogistic.maintenance.clients.feign;

import org.orbitalLogistic.maintenance.dto.common.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/api/users")
public interface UserServiceFeignClient {

    @GetMapping("/{id}")
    UserDTO getUserById(@PathVariable Long id);

    @GetMapping("/{id}/exists")
    Boolean userExists(@PathVariable Long id);
}
