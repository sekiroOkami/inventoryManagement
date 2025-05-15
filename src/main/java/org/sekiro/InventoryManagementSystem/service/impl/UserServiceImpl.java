package org.sekiro.InventoryManagementSystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.sekiro.InventoryManagementSystem.dto.LoginRequest;
import org.sekiro.InventoryManagementSystem.dto.RegisterRequest;
import org.sekiro.InventoryManagementSystem.dto.Response;
import org.sekiro.InventoryManagementSystem.dto.UserDTO;
import org.sekiro.InventoryManagementSystem.entities.User;
import org.sekiro.InventoryManagementSystem.enums.UserRole;
import org.sekiro.InventoryManagementSystem.exceptions.InvalidCredentialsException;
import org.sekiro.InventoryManagementSystem.exceptions.NotFoundException;
import org.sekiro.InventoryManagementSystem.repository.UserRepository;
import org.sekiro.InventoryManagementSystem.security.JwtUtils;
import org.sekiro.InventoryManagementSystem.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static org.sekiro.InventoryManagementSystem.security.JwtUtils.EXPIRATION_TIME_IN_MILLISEC;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // save a user we need to encode the password;
    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper,
                           JwtUtils jwtUtils,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Response registerUser(RegisterRequest registerRequest) {
        Objects.requireNonNull(registerRequest, "RegisterRequest cannot be null!");
        UserRole role = registerRequest.getRole() != null ? registerRequest.getRole() : UserRole.USER;;
        if (registerRequest.getRole() != null) {
            role = registerRequest.getRole();
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User userToSave = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .role(role)
                .build();

        userRepository.save(userToSave);

        return Response.builder()
                .status(200)
                .message("User created successfully.")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword())
            );
            User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new NotFoundException("Email not found."));
            String token = jwtUtils.generateToken(user.getEmail());
            return Response.builder()
                    .status(200)
                    .message("User logged in successfully.")
                    .role(user.getRole())
                    .token(token)
                    .expireTime(Duration.ofMillis(EXPIRATION_TIME_IN_MILLISEC).toString())
                    .build();
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
//        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            throw new InvalidCredentialsException("Password does not match");
//        }
    }

    @Override
    public Response getAllUsers() {
        // latest user is going to be first
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<UserDTO> userDTOS = modelMapper.map(
                users,
                new TypeToken<List<UserDTO>>(){}.getType()
        );

        // filter the user transaction away because user can have many transactions
        userDTOS.forEach(userDTO -> userDTO.setTransactions(null));
        return Response.builder()
                .status(200)
                .message("success")
                .users(userDTOS)
                .build();

    }

    @Override
    public User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = getExistingUser(email);
        user.setTransactions(null);
        return user;
    }

    private User getExistingUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        return user;
    }

    @Override
    public Response updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getName() != null) existingUser.setName(userDTO.getName());
        if (userDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getRole() != null) existingUser.setRole(userDTO.getRole());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(existingUser);
        return Response.builder()
                .status(200)
                .message("User update successful")
                .build();
    }

    @PreAuthorize("hasRole('(ADMIN')")
    @Override
    public Response deleteUser(Long id) {
        userRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("User deleted successfully.")
                .build();
    }

    @Override
    public Response getUserTransactions(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.getTransactions().forEach(transactionDTO -> {
            // user get transactions
            // transaction get user
            // avoid stack overflow
            // get only the data you need, the thing you don't need don't get then don't return them
            transactionDTO.setUsers(null);
            transactionDTO.setSupplier(null);
            // but the product information is needed
        });

        return Response.builder()
                .status(200)
                .message("success")
                .user(userDTO)
                .build();
    }
}
