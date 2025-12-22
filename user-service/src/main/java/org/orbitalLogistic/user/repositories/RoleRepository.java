package org.orbitalLogistic.user.repositories;

import jdk.jfr.Registered;
import org.orbitalLogistic.user.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Registered
public interface RoleRepository  extends CrudRepository<Role, Long> {
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    List<Role> findAll();

    void deleteRoleByName(String name);
}
