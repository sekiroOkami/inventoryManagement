package org.sekiro.InventoryManagementSystem.service;

import org.sekiro.InventoryManagementSystem.dto.LoginRequest;
import org.sekiro.InventoryManagementSystem.dto.RegisterRequest;
import org.sekiro.InventoryManagementSystem.dto.Response;
import org.sekiro.InventoryManagementSystem.dto.UserDTO;
import org.sekiro.InventoryManagementSystem.entities.User;
import org.springframework.stereotype.Service;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getCurrentLoggedInUser();
    Response updateUser(Long id, UserDTO userDTO);
    Response deleteUser(Long id);
    Response getUserTransactions(Long id);
}
