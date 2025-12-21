package org.orbitalLogistic.user.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.orbitalLogistic.user.entities.User;
import org.orbitalLogistic.user.enums.UserRole;
import org.orbitalLogistic.user.exceptions.auth.UsernameAlreadyExistsException;
import org.orbitalLogistic.user.repositories.RoleRepository;
import org.orbitalLogistic.user.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public void create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("");
        }
        userRepository.save(user);
    }

//    protected UserResponseDTO registerUser(SignUpRequestDTO request) {
//        if (userRepository.existsByEmail(request.email())) {
//            throw new UserAlreadyExistsException("User with email already exists");
//        }
//
//        if (userRepository.existsByUsername(request.username())) {
//            throw new UserAlreadyExistsException("User with such username already exists");
//        }
//
//        User user = userMapper.toEntity(request);
//        user.setPasswordHash(request.password());
//        UserRole userRole = roleRepository.findByName("logistics_officer")
//                .orElseThrow(() -> new DataNotFoundException("logistics_officer role not found"));
//        user.setRoleId(userRole.getId());
//
//        user = userRepository.save(user);
//        return toResponseDTO(user);
//    }

//    protected PageResponseDTO<UserResponseDTO> getUsers(String email, String username, int page, int size) {
//        int offset = page * size;
//        List<User> users = userRepository.findUsersWithFilters(email, username, size, offset);
//        long total = userRepository.countUsersWithFilters(email, username);
//
//        List<UserResponseDTO> userDTOs = users.stream().map(this::toResponseDTO).toList();
//
//        int totalPages = (int) Math.ceil((double) total / size);
//        return new PageResponseDTO<>(userDTOs, page, size, total, totalPages, page == 0, page >= totalPages - 1);
//    }

//    protected UserResponseDTO findUserById(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
//        return toResponseDTO(user);
//    }

//    protected UserResponseDTO findUserByEmail(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
//        return toResponseDTO(user);
//    }

//    protected UserResponseDTO updateUser(Long id, UpdateUserRequestDTO request) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
//
//        if (request.username() != null) user.setUsername(request.username());
//
//        user = userRepository.save(user);
//        return toResponseDTO(user);
//    }

    protected void deleteUser(Long id) {
//        if (!userRepository.existsById(id)) {
//            throw new UserNotFoundException("User not found");
//        }
//        userRepository.deleteById(id);
    }

    public User getEntityByIdOrNull(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Boolean userExists(Long id) {
        return userRepository.existsById(id);
    }

    public Boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())   {
            throw new EntityNotFoundException("User not found!");
        }
        return user.get();
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
