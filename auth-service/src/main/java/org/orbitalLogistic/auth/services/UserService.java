package org.orbitalLogistic.auth.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.orbitalLogistic.auth.entities.User;
import org.orbitalLogistic.auth.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    User getUserByUsername() {

    }
}
