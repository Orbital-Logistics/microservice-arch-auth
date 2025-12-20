package org.orbitalLogistic.auth.repositories;

import jdk.jfr.Registered;
import org.orbitalLogistic.auth.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Registered
public interface RoleRepository  extends CrudRepository<Role, Long> {
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
}
