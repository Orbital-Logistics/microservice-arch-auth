package org.orbitalLogistic.user.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.orbitalLogistic.user.entities.User;
import org.orbitalLogistic.user.exceptions.common.DataNotFoundException;
import org.orbitalLogistic.user.exceptions.user.UserAlreadyExistsException;
import org.orbitalLogistic.user.exceptions.user.UserNotFoundException;
import org.orbitalLogistic.user.mappers.UserMapper;
import org.orbitalLogistic.user.repositories.UserRepository;
import org.orbitalLogistic.user.repositories.UserRoleRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceExceptionTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_EmailExists() {
        var req = mock(org.orbitalLogistic.user.dto.request.UserRegistrationRequestDTO.class);
        when(req.email()).thenReturn("a@b.com");
        when(userRepository.existsByEmail("a@b.com")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(req).block());
    }

    @Test
    void registerUser_UsernameExists() {
        var req = mock(org.orbitalLogistic.user.dto.request.UserRegistrationRequestDTO.class);
        when(req.email()).thenReturn("a@b.com");
        when(req.username()).thenReturn("existing");
        when(userRepository.existsByEmail("a@b.com")).thenReturn(false);
        when(userRepository.existsByUsername("existing")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(req).block());
    }

    @Test
    void findUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(1L).block());
    }

    @Test
    void registerUser_RoleNotFound() {
        var req = mock(org.orbitalLogistic.user.dto.request.UserRegistrationRequestDTO.class);
        when(req.email()).thenReturn("x@x.com");
        when(req.username()).thenReturn("u");
        when(req.password()).thenReturn("p");

        when(userRepository.existsByEmail("x@x.com")).thenReturn(false);
        when(userRepository.existsByUsername("u")).thenReturn(false);

        User mappedUser = new User();
        when(userMapper.toEntity(any())).thenReturn(mappedUser);

        when(roleRepository.findByName("logistics_officer")).thenReturn(java.util.Optional.empty());

        assertThrows(DataNotFoundException.class, () -> userService.registerUser(req).block());
    }

    @Test
    void updateUser_NotFound() {
        var req = mock(org.orbitalLogistic.user.dto.request.UpdateUserRequestDTO.class);
        when(userRepository.findById(42L)).thenReturn(java.util.Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(42L, req).block());
    }

    @Test
    void deleteUser_NotFound() {
        when(userRepository.existsById(99L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(99L).block());
    }
}
