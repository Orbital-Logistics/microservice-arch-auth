package org.orbitalLogistic.user.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orbitalLogistic.user.entities.Role;
import org.orbitalLogistic.user.entities.User;
import org.orbitalLogistic.user.exceptions.auth.EmailAlreadyExistsException;
import org.orbitalLogistic.user.exceptions.auth.UnknownRoleException;
import org.orbitalLogistic.user.exceptions.auth.UsernameAlreadyExistsException;
import org.orbitalLogistic.user.exceptions.auth.WrongCredentialsException;
import org.orbitalLogistic.user.repositories.RoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final UserService usersService;
    private final JwtService jwtService;

    private final RoleService roleService;

    @Transactional
    public String signUp(String username, String password, String email, Set<String> roles) {

        if (usersService.userExists(username)) {
            throw new UsernameAlreadyExistsException("");
        }

        if (usersService.emailExists(email)) {
            throw new EmailAlreadyExistsException("");
        }

        Set<Role> validatedRoles = roleService.validateRoles(roles);

        User user = User.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(validatedRoles)
                .enabled(true)
                .build();

        usersService.create(user);

        return jwtService.generateToken(user);
    }

    @Transactional
    public String logIn(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username,
                    password
            ));
        } catch (AuthenticationException e) {
            throw new WrongCredentialsException("");
        }

        var user = usersService
                .userDetailsService()
                .loadUserByUsername(username);

        return jwtService.generateToken(user);
    }
}
