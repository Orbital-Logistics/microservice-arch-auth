package org.orbitalLogistic.user.services;

import lombok.RequiredArgsConstructor;
import org.orbitalLogistic.user.entities.Role;
import org.orbitalLogistic.user.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional(rollbackFor = Exception.class)
    public Role getOrCreateRole(String name) {

        Optional<Role> role = roleRepository.findByName(name);
        if (role.isPresent()) {
            return role.get();
        }

        Role newRole = Role.builder()
                .name(name)
                .build();
        roleRepository.save(newRole);

        return newRole;
    }
}
